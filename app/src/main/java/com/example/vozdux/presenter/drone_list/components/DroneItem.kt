package com.example.vozdux.presenter.drone_list.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.vozdux.domain.model.drone.CompositeDroneElement
import com.example.vozdux.domain.model.drone.Cost
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.DroneProperties
import com.example.vozdux.domain.model.drone.DroneWithImages
import com.example.vozdux.domain.model.drone.USD_CODE

//@Preview
//@Composable
//fun TestFunction() {
//    DroneItem(
//        modifier = Modifier.fillMaxWidth(),
//        element = Drone(
//            name = "ZALA LANCET",
//            shortDescription = "Беспилотный боеприпас, разработанный российской компанией ZALA. Предназначен для поражения наземной техники и других важных целей на переднем крае и в тылу противника." ,
//            longDescription = mutableListOf(
//                CompositeDroneElement(
//                    name = "Описание",
//                    value = "ZALA Lancet может использоваться как для разведывательных, так и для ударных задач, имеет максимальную дальность полета 40—50 км[6] в зависимости от субверсии и максимальную взлетную массу (MTOW) около 12 кг. В боевом режиме он может быть вооружен как осколочно-фугасными, так и кумулятивными боевыми частями.[4][неавторитетный источник]. Использует электродвигатель для снижения заметности в аудио- и тепловом диапазонах, максимальное время полёта - 40 минут[2].\n" +
//                            "\n" +
//                            "Имеет оптико-электронное наведение, а также блок телевизионного наведения, что позволяет управлять боеприпасом на конечном этапе полета[2]. Беспилотник оснащен интеллектуальными, навигационными и коммуникационными модулями. Не использует спутниковую навигацию. Имеет защиту от лазерного оружия[2]. Особенностью БПЛА является, что его модули наведения сменные и могут меняться под задачу. Доступный спектр датчиков достаточно широк: камеры в инфракрасном и видимом диапазоне, наведение на лазерный целеуказатель, ассистирующие датчик поиска целей газоанализатором и радиометром."
//                )
//            ),
//            otherProperties = mutableListOf(),
//            creationDate = "12.09.2023",
//            country = "Russia",
//            imageIDs = mutableListOf(),
//            cost = Cost(
//                value = "122",
//                currency = USD_CODE
//            ),
//            mainProperties = DroneProperties(
//                battery = 1,
//                flightRange = 1,
//                flightTime = 1,
//                maximumFlightHeight = 1,
//                maxVelocity = 1
//            )
//        )
//        ,
//        onClick = {
//
//        })
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DroneItem(
    element: DroneWithImages,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick.invoke(element.drone.id)
        }
    ) {
        Log.d("DEBUGGING", "1")
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (element.images.isNotEmpty())
                AsyncImage(
                model = element.images[0].uri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp),
                contentScale = ContentScale.FillWidth
            ) else {
                Image(
                    modifier = Modifier
                        .size(240.dp),
                    imageVector = Icons.Default.Clear,
                    contentDescription = element.drone.name + "image"
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(text = element.drone.name, style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp))

                Text(
                    text = element.drone.shortDescription,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}