package com.example.workmanager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.workmanager.ui.theme.WorkManagerTheme
import java.io.FileInputStream

class MainActivity : ComponentActivity() {

    private lateinit var completionState : MutableState<Boolean>
    private lateinit var bitmap: MutableState<Bitmap?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            completionState = remember { mutableStateOf(false) }
            bitmap = remember { mutableStateOf(null) }
            WorkManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(completionState.value, Modifier.padding(innerPadding),bitmap.value)
                }
            }
        }
        val workManager = WorkManager.getInstance(this)
        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
            .addTag("DOWNLOAD_WORKER")
            .build()
        workManager.enqueue(request)
        workManager.getWorkInfoByIdLiveData(request.id).observe(this){workInfo ->
            when(workInfo?.state){

                WorkInfo.State.RUNNING -> Log.d("asd -->","Downloading Image")
                WorkInfo.State.SUCCEEDED -> {
                    Log.d("asd -->","image downloaded successfully")
                    val fileName = workInfo.outputData.getString("File Name")
                    val fis: FileInputStream = openFileInput(fileName)
                    bitmap.value= BitmapFactory.decodeStream(fis)
                    completionState.value = true
                }
                else ->{
                    Log.d("asd -->","else branch: state = ${workInfo?.state}")
                }
            }
        }
    }
}

@Composable
fun MainScreen(isCompleted: Boolean, modifier: Modifier = Modifier, imageURI: Bitmap?){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(isCompleted){
            Image(
                bitmap = imageURI!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }else{
            CircularProgressIndicator()

        }

    }
}


