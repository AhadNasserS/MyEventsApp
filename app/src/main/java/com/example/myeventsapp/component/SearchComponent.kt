package com.example.myeventsapp.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

//@Composable
//fun Search(onSearch: (String) -> Unit){
//    var query by remember { mutableStateOf("") }
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .padding(16.dp)){
//        Row(
//            modifier= Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 8.dp, vertical = 4.dp)
//                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium),
//            verticalAlignment = Alignment.CenterVertically
//        ){
//            Icon(imageVector = Icons.Default.Search
//                , contentDescription = "Search icon",
//            modifier=Modifier.size(24.dp))
//
//            Spacer(modifier = Modifier.width(8.dp))
//
//            TextField(value = query,
//                onValueChange = {query= it
//                onSearch(it)},
//                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp),
//                singleLine = true,
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 8.dp, vertical = 12.dp)
//                )
//            if(query.isNotEmpty()){
//                Icon(imageVector = Icons.Default.Clear
//                    , contentDescription = "Clear icon",
//                modifier= Modifier.size(24.dp)
//                    .clickable { query= ""
//                        onSearch("")
//                    }
//                )
//            }
//
//        }
//
//    }
//
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(onSearch: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    SearchBar(query = query, onQueryChange = { query = it }, onSearch = {
        onSearch(it)
        active = false
    },
        active = active, onActiveChange = { active = it },
        placeholder = {
            Text(text = "Search for task")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Clear, contentDescription = "",
                modifier = Modifier.clickable {
                    if (query != "") {
                        query = ""
                    }
                })
        },
        modifier = Modifier.fillMaxWidth()
    ) {
    }
}