package com.example.inventory.ui.wether

enum class WetherType {
   MoonCloudFastWind, MoonCloudMidRain,SunCloudAngledRain,SunCloudMidRain,Tornado
}

data class WetherItem(
   var Temp:String="0°",
   var TempRange:String="H:20° L:10°",
   var Place:String="Bangalore",
   var WetherType:WetherType=com.example.inventory.ui.wether.WetherType.MoonCloudFastWind
)


fun getWetherList():List<WetherItem>{
    return listOf(
        WetherItem("30°","H:30° L:23°","Hyderabad",WetherType.MoonCloudMidRain),
        WetherItem("42°","H:50° L:35°","Rajasthan",WetherType.SunCloudAngledRain),
        WetherItem("32°","H:38° L:36°","Bangalore",WetherType.MoonCloudFastWind),
        WetherItem("28°","H:40° L:13°","Mumbai",WetherType.SunCloudMidRain),
        WetherItem("20°","H:30° L:18°","Kashmir",WetherType.Tornado),
    )

}

