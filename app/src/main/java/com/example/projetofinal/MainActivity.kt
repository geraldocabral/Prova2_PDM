package com.example.projetofinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetofinal.ui.theme.ProjetoFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                menu()

        }
    }
}

@Composable
fun menu(){
    val contexto = LocalContext.current

    Column(Modifier.padding(40.dp)) {
        Text(text = "Fazendas", textAlign = TextAlign.Center, modifier = Modifier.width(300.dp))
        Spacer(modifier = Modifier.height(45.dp))
        Button(onClick = {
            contexto.startActivity(Intent(contexto, TelaInsercao::class.java))
        },
        modifier = Modifier.width(300.dp)) {
            Text(text = "Inserir Fazenda")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            contexto.startActivity(Intent(contexto, TelaMostrar::class.java))
        },
            modifier = Modifier.width(300.dp)) {
            Text(text = "Visualizar fazendas")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            contexto.startActivity(Intent(contexto, TelaBusca::class.java))
        },
            modifier = Modifier.width(300.dp)) {
            Text(text = "Buscar fazendas")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            contexto.startActivity(Intent(contexto, TelaMedia::class.java))
        },
            modifier = Modifier.width(300.dp)) {
            Text(text = "Consultar valor médio")
        }

    }
}