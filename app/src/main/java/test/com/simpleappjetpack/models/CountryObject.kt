package test.com.simpleappjetpack.models

class CountryObject {
    data class Data(
            val id : Int,
            val name : String,
            val country : String,
            val coord : Coord
    )
    data class Coord(
            val lon : Float,
            val lat : Float
    )
}