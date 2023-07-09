package com.example.projetofinal

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings.TextSize
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.projetofinal.ui.theme.ProjetoFinalTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class TelaMedia: ComponentActivity() {
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
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("mensagem", "$error")
            }
        })
        setContent {
            ProjetoFinalTheme {
                // A surface container using the 'background' color from the theme

                calculaMedia(listaFazenda)
            }
        }
    }
}

@Composable
fun calculaMedia(listaFazenda: SnapshotStateList<Fazenda>) {

    val contexto  = LocalContext.current

    val activity = (contexto as? Activity)

    var valorTotal = 0.0


    for (element in listaFazenda){
        valorTotal += element.valorDaPropriedade
    }

    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally

    ) {
        Text(text = "Valor médio das propriedades cadastradas",
            textAlign = TextAlign.Center
            )
        Spacer(Modifier.height(20.dp))

        Text(text = "${valorTotal / listaFazenda.size}",
            textAlign = TextAlign.Center
            )

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = { activity?.finish() }, modifier = Modifier.defaultMinSize(15.dp, 15.dp)) {
            Text(text = "Voltar")
        }
    }

}

