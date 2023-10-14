package com.example.vozdux.domain.model.drone

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vozdux.constants.DRONES_LOCAL_DATABASE_NAME
import com.example.vozdux.constants.EMPTY_ID
import com.example.vozdux.constants.EMPTY_STRING
import java.util.UUID


@Entity(tableName = DRONES_LOCAL_DATABASE_NAME)
data class Drone (
    val name: String,
    val shortDescription: String,
    val longDescription: MutableList<CompositeDroneElement>,
    val otherProperties: MutableList<CompositeDroneElement>,
    val mainProperties: DroneProperties,
    val creationDate: String,
    val country: String,
    val cost: Cost,
    val imageIDs: List<ImageSourceId>, // Id, source
    @PrimaryKey val id: String = EMPTY_ID,
) {

    constructor() : this(
        name = "ZALA LANCET",
        shortDescription = "Беспилотный боеприпас, разработанный российской компанией ZALA. Предназначен для поражения наземной техники и других важных целей на переднем крае и в тылу противника. Производитель также проводит эксперименты по поражению крупных вражеских ударных БПЛА в полете." ,
        longDescription = mutableListOf(
            CompositeDroneElement(
                name = "Описание",
                value = "ZALA Lancet может использоваться как для разведывательных, так и для ударных задач, имеет максимальную дальность полета 40—50 км[6] в зависимости от субверсии и максимальную взлетную массу (MTOW) около 12 кг. В боевом режиме он может быть вооружен как осколочно-фугасными, так и кумулятивными боевыми частями.[4][неавторитетный источник]. Использует электродвигатель для снижения заметности в аудио- и тепловом диапазонах, максимальное время полёта - 40 минут[2].\n" +
                        "\n" +
                        "Имеет оптико-электронное наведение, а также блок телевизионного наведения, что позволяет управлять боеприпасом на конечном этапе полета[2]. Беспилотник оснащен интеллектуальными, навигационными и коммуникационными модулями. Не использует спутниковую навигацию. Имеет защиту от лазерного оружия[2]. Особенностью БПЛА является, что его модули наведения сменные и могут меняться под задачу. Доступный спектр датчиков достаточно широк: камеры в инфракрасном и видимом диапазоне, наведение на лазерный целеуказатель, ассистирующие датчик поиска целей газоанализатором и радиометром."
            )
        ),
        otherProperties = mutableListOf(),
        creationDate = "12.09.2023",
        country = "Russia",
        imageIDs = mutableListOf(),
        cost = Cost(
            value = "122",
            currency = USD_CODE
        ),
        mainProperties = DroneProperties(
            battery = 1,
            flightRange = 1,
            flightTime = 1,
            maximumFlightHeight = 1,
            maxVelocity = 1
        )
    )
}

data class ImageSourceId(
    val id: String = EMPTY_STRING,
    val source: String = EMPTY_STRING
)
data class CompositeDroneElement(
    val name: String = EMPTY_STRING,
    val value: String = EMPTY_STRING,
    val id: String = UUID.randomUUID().toString()
)







