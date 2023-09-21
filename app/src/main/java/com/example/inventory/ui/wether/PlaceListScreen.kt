package com.example.inventory.ui.wether

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme
object WetherListDestination : NavigationDestination {
    override val route = "wether_list"
    override val titleRes = R.string.lbl_weather
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .paint(
                painterResource(id = R.drawable.stars_wallpaper),
                contentScale = ContentScale.Fit
            ),
        topBar = {
            InventoryTopAppBar(
                title = stringResource(WetherListDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                modifier = Modifier.background(
                    Brush.linearGradient(colors= listOf( Color(0xFFF04393),
                        Color(0xFFF9C449)))
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier
            .paint(painterResource(id = R.drawable.stars_wallpaper), contentScale = ContentScale.FillBounds, alpha = 0.8f)
            .padding(innerPadding)
            .fillMaxSize()){
            CityList(getWetherList())

        }

    }
}



@Composable
private fun CityList(itemList: List<WetherItem>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = itemList, key = { it.Place }) { item ->
            WetherItemScreen(item = item)

        }
    }
}
@Composable
private fun WetherItemScreen(
    item: WetherItem, modifier: Modifier = Modifier
) {

    Box(modifier = modifier
        .fillMaxWidth()
        .height(250.dp)
        .padding(start = 10.dp, end = 10.dp),
    )

    {
        Image(
            painter = painterResource(id = R.drawable.rectangle_wether_card),
            modifier = Modifier.fillMaxSize()
                .shadow(elevation = 150.dp, spotColor = Color(0xB23B267B), ambientColor = Color(0xB23B267B))
            ,
            contentDescription = "Background Image",
            contentScale = ContentScale.Fit

        )
        Image(painter = painterResource(id = getImageReference(item.WetherType)), contentDescription = "",
            Modifier
                .size(200.dp)
                .align(Alignment.TopEnd),)


        Column {
            Text(text = item.Temp,
                fontSize=60.sp,
                color=Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top=80.dp,start=20.dp))

            Text(text = item.TempRange,
               modifier= Modifier.padding(start=20.dp),
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0x99EBEBF5),)


            )

            Text(text = item.Place,
                modifier= Modifier.padding(start=20.dp),
                style = TextStyle(
                    fontSize = 25.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color.White,))
        }




    }

}

fun getImageReference(type:WetherType):Int{
    return when(type){
        WetherType.Tornado->R.drawable.tornado
        WetherType.MoonCloudFastWind->R.drawable.moon_cloud_fast_wind
        WetherType.MoonCloudMidRain->R.drawable.moon_cloud_mid_rain
        WetherType.SunCloudAngledRain->R.drawable.sun_cloud_angled_rain
        WetherType.SunCloudMidRain->R.drawable.sun_cloud_mid_rain
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {

    InventoryTheme {
       // WetherItemScreen(item = WetherItem("30°","H:30° L:23°","Hyderabad",WetherType.MoonCloudMidRain),
          //  CityList(itemList = getWetherList())
        CityListScreen({})

    }
}