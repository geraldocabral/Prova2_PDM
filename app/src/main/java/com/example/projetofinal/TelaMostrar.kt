package com.example.projetofinal

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.projetofinal.ui.theme.ProjetoFinalTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class TelaMostrar : ComponentActivity() {
    private lateinit var referencia : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        referencia = Firebase.database.reference

        var listaFazenda = mutableStateListOf<Fazenda>()

        referencia.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var gson = Gson()
                    for (i in snapshot.children) {

                        val json = gson.toJson(i.value)
                        val farm = gson.fromJson(json, Fazenda::class.java)

                        listaFazenda.add(
                            Fazenda(
                                farm.cod,
                                farm.nome,
                                farm.valorDaPropriedade,
                                farm.qtdeFuncionarios
                            )
                        )

                        Log.i("teste", "${listaFazenda}")
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("mensagem", "$error")
            }
        })

        setContent {
            mostra(listaFazenda)
        }

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun mostra(listaFazenda: MutableList<Fazenda>) {

    Log.i("teste", "${ listaFazenda }")
    val list = rememberLazyListState()
    val contexto = LocalContext.current


    val activity = (contexto as? Activity)



    Column(Modifier.padding(40.dp)) {
        Text(
            text = "Fazendas cadastradas",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))
        Scaffold(content = {
            LazyColumn(
                state = list,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {

                items(
                    items = listaFazenda,
                    itemContent = {
                        ItemDaListaFazenda(fazenda = it)

                    })
            }
        }
        )
    }

    Spacer(modifier = Modifier.height(15.dp))

    Button(onClick = { activity?.finish() }, modifier = Modifier.defaultMinSize(15.dp, 15.dp)) {
        Text(text = "Voltar")
    }
}

    @Composable
    fun ItemDaListaFazenda(fazenda: Fazenda) {

        Text(text = "Cod.: ${fazenda.cod} | Nome: ${fazenda.nome} | Valor: ${fazenda.valorDaPropriedade} | Funcionarios: ${fazenda.qtdeFuncionarios}")
    }



