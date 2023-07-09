package com.example.projetofinal

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.projetofinal.ui.theme.ProjetoFinalTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID

class TelaInsercao : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Insercao()
        }
    }
}
private lateinit var referencia : DatabaseReference

@Composable
fun Insercao() {

    referencia = Firebase.database.reference
    val contexto  = LocalContext.current
    var estadoCampoDeTextoCod = remember {
        mutableStateOf(TextFieldValue())
    }
    var estadoCampoDeTextoNome = remember {
        mutableStateOf(TextFieldValue())
    }
    var estadoCampoDeTextoValor = remember {
        mutableStateOf(TextFieldValue())
    }
    var estadoCampoDeTextoQtdeFuncionarios = remember {
        mutableStateOf(TextFieldValue())
    }

    val activity = (contexto as? Activity)
    
    
    Column(Modifier.padding(40.dp)) {

        Text(text = "Cadastro de Fazenda", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(25.dp))
        TextField(
            value = estadoCampoDeTextoCod.value, onValueChange = {
                estadoCampoDeTextoCod.value = it
            },
            placeholder = { Text(text = "Codigo")},
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
            placeholder = { Text(text = "Nome")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = estadoCampoDeTextoValor.value, onValueChange = {
                estadoCampoDeTextoValor.value = it
            },
            placeholder = { Text(text = "Valor da Propriedade")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = estadoCampoDeTextoQtdeFuncionarios.value, onValueChange = {
                estadoCampoDeTextoQtdeFuncionarios.value = it
            },
            placeholder = { Text(text = "Quantidade de funcionários")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None, autoCorrect = true),
            textStyle = TextStyle(color = Color.Black, fontSize = TextUnit.Unspecified, fontFamily = FontFamily.SansSerif),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            val valoresFazenda = ContentValues().apply {
                put("codigo", estadoCampoDeTextoCod.value.text)
                put("nome", estadoCampoDeTextoNome.value.text)
                put("valor", estadoCampoDeTextoValor.value.text)
                put("qtde", estadoCampoDeTextoQtdeFuncionarios.value.text)

            }
            estadoCampoDeTextoCod.value = TextFieldValue("")
            estadoCampoDeTextoNome.value = TextFieldValue("")
            estadoCampoDeTextoValor.value = TextFieldValue("")
            estadoCampoDeTextoQtdeFuncionarios.value = TextFieldValue("")

            val fazenda = Fazenda(valoresFazenda.getAsString("codigo"), valoresFazenda.getAsString("nome"), valoresFazenda.getAsDouble("valor"), valoresFazenda.getAsInteger("qtde"))
            referencia.child(fazenda.cod).setValue(fazenda)



        },
            modifier = Modifier.fillMaxWidth()
            ) {
            Text(text = "Inserir")
        }
        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = { activity?.finish() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Voltar")
        }

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ProjetoFinalTheme {
        Insercao()
    }
}