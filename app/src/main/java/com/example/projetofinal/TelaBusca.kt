package com.example.projetofinal

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.projetofinal.ui.theme.ProjetoFinalTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private lateinit var referencia : DatabaseReference
class TelaBusca : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjetoFinalTheme {
                buscaFazenda()

            }
        }
    }
}



@Composable
fun buscaFazenda() {

    referencia = Firebase.database.reference

    val contexto  = LocalContext.current
    var estadoCampoDeTextoCod = remember {
        mutableStateOf(TextFieldValue())
    }
    var estadoCampoDeTextoNome = remember {
        mutableStateOf(TextFieldValue())
    }

    var estadoCampoDeTextoAttNome = remember {
        mutableStateOf(TextFieldValue())
    }
    var estadoCampoDeTextoAttValor = remember {
        mutableStateOf(TextFieldValue())
    }
    var estadoCampoDeTextoAttFunc = remember {
        mutableStateOf(TextFieldValue())
    }

    var fazenda = remember {
        mutableStateOf<Fazenda?>(null)
    }

    val activity = (contexto as? Activity)



    Column(
        Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState(), enabled = true)) {
        TextField(
            value = estadoCampoDeTextoCod.value, onValueChange = {
                estadoCampoDeTextoCod.value = it
            },
            placeholder = { Text(text = "Codigo") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = estadoCampoDeTextoNome.value, onValueChange = {
                estadoCampoDeTextoNome.value = it
            },
            placeholder = { Text(text = "Nome") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            Modifier
                .padding(40.dp)
                .align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                if (estadoCampoDeTextoCod.value.text.isEmpty() && estadoCampoDeTextoNome.value.text.isEmpty()){
                    Toast.makeText(contexto, "Preencha um dos campos a cima para realizar a busca", Toast.LENGTH_LONG).show()
                } else {
                    if (estadoCampoDeTextoCod.value.text.isNotEmpty()){
                        val valorBusca = estadoCampoDeTextoCod.value.text
                        val busca = referencia.orderByChild("cod").equalTo(valorBusca)

                        busca.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()){
                                    val fazendaSnapshot = snapshot.children.first()
                                    val cod = fazendaSnapshot.child("cod").getValue(String::class.java).toString()
                                    val nome = fazendaSnapshot.child("nome").getValue(String::class.java).toString()
                                    val valor = fazendaSnapshot.child("valorDaPropriedade").getValue(Double::class.java)!!
                                        .toDouble()
                                    val qtde = fazendaSnapshot.child("qtdeFuncionarios").getValue(Int::class.java)!!
                                        .toInt()

                                    fazenda.value = Fazenda(cod,nome,valor,qtde)


                                    Log.i("teste", "${fazenda}");
                                } else {
                                    Toast.makeText(contexto, "O valor de codigo informado não se encontra no banco de dados", Toast.LENGTH_LONG).show()
                                }

                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.i("mensagem", "${error}")
                            }
                        })
                    } else {
                        val valorBusca = estadoCampoDeTextoNome.value.text
                        val busca = referencia.orderByChild("nome").equalTo(valorBusca)

                        busca.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()){
                                    val fazendaSnapshot = snapshot.children.first()
                                    val cod = fazendaSnapshot.child("cod").getValue(String::class.java).toString()
                                    val nome = fazendaSnapshot.child("nome").getValue(String::class.java).toString()
                                    val valor = fazendaSnapshot.child("valorDaPropriedade").getValue(Double::class.java)!!
                                        .toDouble()
                                    val qtde = fazendaSnapshot.child("qtdeFuncionarios").getValue(Int::class.java)!!
                                        .toInt()

                                    fazenda.value = Fazenda(cod,nome,valor,qtde)


                                    Log.i("teste", "${fazenda}");
                                } else {
                                    Toast.makeText(contexto, "O nome informado não se encontra no banco de dados", Toast.LENGTH_LONG).show()
                                }

                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.i("mensagem", "${error}")
                            }
                        })
                    }
                }
                estadoCampoDeTextoCod.value = TextFieldValue("")
                estadoCampoDeTextoNome.value = TextFieldValue("")
            },Modifier.size(120.dp,35.dp))
            {
                Text(text = "Buscar")
            }
            Spacer(modifier = Modifier.width(15.dp))

            Button(onClick = { activity?.finish() },Modifier.size(120.dp,35.dp)) {
                Text(text = "Voltar")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        if(fazenda.value == null){
            Text(text = "Esperando informações para busca...")
        } else {

            Text(text = "Codigo:")
            TextField(
                value = fazenda.value!!.cod, onValueChange = {},
                
                textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Nome:")
            TextField(
                value = estadoCampoDeTextoAttNome.value, onValueChange = {
                    estadoCampoDeTextoAttNome.value = it
                },
                placeholder = { Text(text = "${fazenda.value!!.nome}")},
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
                textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Valor da fazenda:")
            TextField(
                value = estadoCampoDeTextoAttValor.value, onValueChange = {
                    estadoCampoDeTextoAttValor.value = it
                },
                placeholder = { Text(text = "${fazenda.value!!.valorDaPropriedade}")},
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
                textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Quantidade de funcionários:")
            TextField(
                value = estadoCampoDeTextoAttFunc.value, onValueChange = {
                    estadoCampoDeTextoAttFunc.value = it
                },
                placeholder = { Text(text = "${fazenda.value!!.qtdeFuncionarios}")},
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
                textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                Modifier
                    .padding(40.dp)
                    .align(Alignment.CenterHorizontally)) {
                Button(onClick = {
                    if(estadoCampoDeTextoAttNome.value.text.isNotEmpty()){
                        fazenda.value!!.nome = estadoCampoDeTextoAttNome.value.text
                    }
                    if(estadoCampoDeTextoAttValor.value.text.isNotEmpty()){
                        fazenda.value!!.valorDaPropriedade = estadoCampoDeTextoAttValor.value.text.toDouble()
                    }
                    if(estadoCampoDeTextoAttFunc.value.text.isNotEmpty()){
                        fazenda.value!!.qtdeFuncionarios = estadoCampoDeTextoAttFunc.value.text.toInt()
                    }

                    var attFazenda = Fazenda(fazenda.value!!.cod, fazenda.value!!.nome, fazenda.value!!.valorDaPropriedade, fazenda.value!!.qtdeFuncionarios)

                    referencia.child(fazenda.value!!.cod).setValue(attFazenda)

                }, modifier = Modifier.size(120.dp,35.dp)) {
                    Text(text = "Atualizar")
                }

                Spacer(modifier = Modifier.width(15.dp))
                Button(onClick = {
                    referencia.child("${fazenda.value!!.cod}").removeValue()
                }, Modifier.size(120.dp,35.dp)) {
                    Text(text = "Excluir")
                }
            }


        }

    }


}



