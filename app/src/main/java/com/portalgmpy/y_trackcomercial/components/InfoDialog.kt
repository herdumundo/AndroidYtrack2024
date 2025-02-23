package com.portalgmpy.y_trackcomercial.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.portalgmpy.y_trackcomercial.ui.inventario.screen.AreShapeOnBorderCenterSurface

@Composable
fun InfoDialog(
    title: String? = "Message",
    desc: String? = "Your Message",
    image: Int,
    funcion: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFDF7F7),
                        shape = RoundedCornerShape(20.dp, 20.dp, 50.dp, 50.dp)
                    )
                    .align(Alignment.BottomCenter),
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(175.dp)

                        .fillMaxWidth(),
                    )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Text: title
                    Text(
                        text = title!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button

                    Row() {
                        Button(
                            onClick = { onDismiss()},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE0303)),
                        ) {
                            Text(
                                text = "No",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFFB9B3B3),
                            )
                        }
                        Spacer(modifier = Modifier.width(24.dp))
                        Button(
                            onClick = { onDismiss(); funcion()  },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE0303)),
                        ) {
                            Text(
                                text = "Si",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                        }
                    }
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}



@Composable
fun InfoDialogOk(
    title: String,
    desc: String,
    image: Int,
    funcion: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFDF7F7),
                        shape = RoundedCornerShape(20.dp, 20.dp, 50.dp, 50.dp)
                    )
                    .align(Alignment.BottomCenter),
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(175.dp)

                        .fillMaxWidth(),
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Text: title
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button

                    Row() {
                        Button(
                            onClick = { onDismiss();funcion()},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE0303)),
                            //.clip(RoundedCornerShape(25.dp))
                        ) {
                            Text(
                                text = "Aceptar",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFFFDFDFD),
                            )
                        }

                    }
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}



@Composable
fun InfoDialogUnBoton(
    title: String,
    titleBottom: String,
    desc: String,
    image: Int,
    funcion: () -> Unit,
 ) {
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
            //    .fillMaxWidth()
                .width(390.dp)
              //  .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFDF7F7),
                        shape = RoundedCornerShape(20.dp, 20.dp, 50.dp, 50.dp)
                    )
                    .align(Alignment.BottomCenter),
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(175.dp)

                        .fillMaxWidth(),
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Text: title
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button

                    Row() {
                        Button(
                            onClick = {funcion() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE0303)),
                            //.clip(RoundedCornerShape(25.dp))
                        ) {
                            Text(
                                text = titleBottom,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFFFDFDFD),
                            )
                        }

                    }
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}





@Composable
fun InfoDialogDosBoton(
    title: String,
    titleBottom: String,
    desc: String,
    image: Int,
    funcion: () -> Unit,
    funcionCancel: () -> Unit,
) {
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                //    .fillMaxWidth()
                .width(390.dp)
                //  .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFDF7F7),
                        shape = RoundedCornerShape(20.dp, 20.dp, 50.dp, 50.dp)
                    )
                    .align(Alignment.BottomCenter),
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(175.dp)

                        .fillMaxWidth(),
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Text: title
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button

                    Row() {


                        Button(
                            onClick = {funcionCancel() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000000)),
                            //.clip(RoundedCornerShape(25.dp))
                        ) {
                            Text(
                                text = "Cancelar",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFFFDFDFD),
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {funcion() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE0303)),
                            //.clip(RoundedCornerShape(25.dp))
                        ) {
                            Text(
                                text = titleBottom,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFFFDFDFD),
                            )
                        }

                    }
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}




@Composable
fun InfoDialogDosBotonBoolean(
    title: String,
    titleBottom: String,
    desc: String,
    image: Int,
    isActive: Boolean,
    funcion: () -> Unit,
    funcionCancel: () -> Unit,
) {
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                //    .fillMaxWidth()
                .width(390.dp)
                //  .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFDF7F7),
                        shape = RoundedCornerShape(20.dp, 20.dp, 50.dp, 50.dp)
                    )
                    .align(Alignment.BottomCenter),
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(175.dp)

                        .fillMaxWidth(),
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Text: title
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button

                    Column() {

                        if(isActive){
                            Button(
                                shape = RoundedCornerShape(4.dp),

                                modifier=Modifier.fillMaxWidth(),
                                onClick = {funcion() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE0303)),
                                //.clip(RoundedCornerShape(25.dp))
                            ) {
                                Text(
                                    text = titleBottom,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color(0xFFFDFDFD),
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))


                    }
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(5.dp))

                    Button(
                        shape = RoundedCornerShape(4.dp),

                        modifier=Modifier.fillMaxWidth(),
                        onClick = {funcionCancel() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000000)),
                        //.clip(RoundedCornerShape(25.dp))
                    ) {
                        Text(

                            text = "Cancelar",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color(0xFFFDFDFD),
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun InfoDialogSinBoton(
    title: String,
     desc: String,
    image: Int,
    funcion: () -> Unit,
) {
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                //    .fillMaxWidth()
                .width(390.dp)
                //  .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFDF7F7),
                        shape = RoundedCornerShape(20.dp, 20.dp, 50.dp, 50.dp)
                    )
                    .align(Alignment.BottomCenter),
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(175.dp)

                        .fillMaxWidth(),
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Text: title
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }


}
@Composable
fun cuadroAvisoDenegado(Texto: String  ) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        AreShapeOnBorderCenterSurface(
            cornerRadius = 15.dp,
            centerCircleRadius = 50.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material.Text(text = "Error", color = Color.Red, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(10.dp))
                androidx.compose.material.Text(text = "Lo siento!", color = Color.Black, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(17.dp))
                androidx.compose.material.Text(text = Texto , color = Color.Gray, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}