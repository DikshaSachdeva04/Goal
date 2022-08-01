package com.example.goal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import androidx.compose.material.icons.filled.ExpandLess
//import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowFull()
        }
    }
}
@Composable
fun InputTextField(
    labelText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    singleLine: Boolean = true,
    value: String,
    onValueChange: (String) -> Unit,
   modifier: Modifier = Modifier.fillMaxWidth(.9f),
    isError: Boolean = false,
    trailingIcon: @Composable () -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        label = {
            Text(text = labelText)
        },
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        value = value,
       modifier = modifier,
        onValueChange = onValueChange,
        isError = isError,
        trailingIcon = trailingIcon,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation
    )
}
@Composable
fun Title(){
    val title = rememberSaveable {
        mutableStateOf("")
    }
    val titleError = rememberSaveable {
        mutableStateOf(false)
    }
    InputTextField(
        labelText = "Title",
        singleLine = true,
        value = title.value,
        onValueChange = {
            if (titleError.value) {
                titleError.value = false
            }
            title.value = it
        },
        isError = titleError.value

        )
}



@Composable
fun Description(singleLine: Boolean) {
    val desc = rememberSaveable {
        mutableStateOf("")
    }
    val descError = rememberSaveable {
        mutableStateOf(false)}

    InputTextField(
        labelText = "Description",
        singleLine = false,
        value = desc.value,
        onValueChange = {
            if (descError.value) {
                descError.value = false
            }
            desc.value = it
        },
        isError = descError.value )


}
@Composable
fun goalsCategoryValue(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = modifier
    ) {
        Image(painterResource(id = drawable),
            contentDescription =null ,
            modifier = Modifier.clip(CircleShape)
        )
        Text(
            text = stringResource(text),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )


    }

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun categoryElement() {
    Surface() {
        Text(text = "Goal Category",
            style = typography.h4)
        LazyVerticalGrid(
            cells = GridCells.Fixed(3)
        ) {
            items(goalsCategoryValue) { item ->
                goalsCategoryValue(item.drawable, item.text)

            }
        }
    }
}

@Composable
fun CustomChip(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier
) {

    Surface(
        color = when {
            selected -> MaterialTheme.colors.onSurface
            else -> Color.Transparent
        },
        contentColor = when {
            selected -> MaterialTheme.colors.onPrimary
            else -> Color.LightGray
        },
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> MaterialTheme.colors.primary
                else -> Color.LightGray
            }
        ),
        modifier = modifier
    ) {
        // Add text to show the data that we passed
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = typography.body2,
            modifier = Modifier.padding(8.dp)
        )

    }
}


@Composable
private fun CustomImageChip(
    text: String,
    imageId: Int,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    // define properties to the chip
    // such as color, shape, width
    Surface(
        color = when {
            selected -> MaterialTheme.colors.primary
            else -> Color.Transparent
        },
        contentColor = when {
            selected -> MaterialTheme.colors.onPrimary
            else -> Color.LightGray
        },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> MaterialTheme.colors.primary
                else -> Color.LightGray
            }
        ),
        modifier = modifier
    ) {
        // Inside a Row pack the Image and text together to
        // show inside the chip
        Row(modifier = Modifier) {
            Image(
                painter = painterResource(imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp)
                    .clip(CircleShape)

            )
            Text(
                text = text,
                style = typography.body2,
                modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun Chips() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
    ) {
//        items(goalsCategoryValue) { item ->
//            CustomImageChip(item.drawable, item.text, selected =true, modifier = Modifier)
        items(9) { index ->
            CustomImageChip(
                text = "Work and Career",
                imageId = R.drawable.workandcareer,

                selected = true
            )
            Spacer(modifier = Modifier.padding(3.dp))
            CustomImageChip(
                text = "Health and Wellness",
                imageId = R.drawable.health,
                selected = false,

                )
            Spacer(modifier = Modifier.padding(3.dp))
            CustomImageChip(
                text = "Personal Growth",
                imageId = R.drawable.personal,
                selected = false,

                )
            Spacer(modifier = Modifier.padding(3.dp))
            CustomImageChip(
                text = "Money and Finances",
                imageId = R.drawable.money,
                selected = false,

                )
            Spacer(modifier = Modifier.padding(3.dp))
            CustomImageChip(
                text = "Family and Friends",
                imageId = R.drawable.familyandfriends,
                selected = false,

                )
            Spacer(modifier = Modifier.padding(3.dp))
            CustomImageChip(
                text = "Spirituality and Faith",
                imageId = R.drawable.spirituality,
                selected = false,

                )
            Spacer(modifier = Modifier.padding(3.dp))
            CustomImageChip(
                text = "Lifestyle",
                imageId = R.drawable.lifestyle,
                selected = false,

                )
            Spacer(modifier = Modifier.padding(3.dp))

            CustomImageChip(
                text = "Love and Relationship",
                imageId = R.drawable.loveandrelationship,
                selected = false,

                )
            Spacer(modifier = Modifier.padding(3.dp))
            CustomImageChip(
                text = "Other Goals",
                imageId = R.drawable.other,
                selected = false,

                )
            Spacer(modifier = Modifier.padding(3.dp))
        }
    }
}

//    Row {
//        Column(modifier = Modifier.padding(8.dp)) {
//            // creates a custom chip for active state

//            // Create a custom image chip whose state is active
//            CustomImageChip(
//                text = "Work and Career",
//                imageId = R.drawable.workandcareer,
//                contentScale = ContentScale.Crop,
//                selected = true
//            )
//            Spacer(modifier = Modifier.padding(3.dp))
//            CustomImageChip(
//                text = "Health and Wellness",
//                imageId = R.drawable.health,
//                selected = false,
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.padding(3.dp))
//            CustomImageChip(
//                text = "Personal Growth",
//                imageId = R.drawable.personal,
//                selected = false,
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.padding(3.dp))
//            CustomImageChip(
//                text = "Money and Finances",
//                imageId = R.drawable.money,
//                selected = false,
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.padding(3.dp))
//            CustomImageChip(
//                text = "Family and Friends",
//                imageId = R.drawable.familyandfriends,
//                selected = false,
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.padding(3.dp))
//            CustomImageChip(
//                text = "Spirituality and Faith",
//                imageId = R.drawable.spirituality,
//                selected = false,
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.padding(3.dp))
//            CustomImageChip(
//                text = "Lifestyle",
//                imageId = R.drawable.lifestyle,
//                selected = false,
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.padding(3.dp))
//
//            CustomImageChip(
//                text = "Love and Relationship",
//                imageId = R.drawable.loveandrelationship,
//                selected = false,
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.padding(3.dp))
//            CustomImageChip(
//                text = "Other Goals",
//                imageId = R.drawable.other,
//                selected = false,
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.padding(3.dp))
//
//
//        }
//
//    }


// Function to show a text message
@Composable
fun SubtitleText(subtitle: String, modifier: Modifier = Modifier) {
    Text(text = subtitle, style = typography.subtitle2, modifier = modifier.padding(8.dp))
}


private val goalsCategoryValue = listOf(
    R.drawable.workandcareer to R.string.workanndcareer,
    R.drawable.health to R.string.health,
    R.drawable.personal to R.string.personal,
    R.drawable.money to R.string.money,
    R.drawable.familyandfriends to R.string.family,
    R.drawable.spirituality to R.string.spirituality,
    R.drawable.lifestyle to R.string.lifestyle,

    R.drawable.loveandrelationship to R.string.love,
    R.drawable.other to R.string.other
).map { DrawableStringPair(it.first, it.second) }


@Composable
fun MilestoneTitle(padding: Modifier) {
    val title = rememberSaveable {
        mutableStateOf("")
    }
    val titleError = rememberSaveable {
        mutableStateOf(false)
    }
    InputTextField(
        labelText = "Title",
        singleLine = true,
        modifier= Modifier
            .width(120.dp)
            .height(2.dp)
            .padding(4.dp)
            .size(20.dp),

        value = title.value,
        onValueChange = {
            if (titleError.value) {
                titleError.value = false
            }
            title.value = it
        },
        isError = titleError.value,

        )


}
@Composable
fun MileStoneDescription(padding: Modifier) {
    val desc = rememberSaveable {
        mutableStateOf("")
    }
    val descError = rememberSaveable {
        mutableStateOf(false)}

    InputTextField(
        labelText = "Description",
        singleLine = false,
        modifier= Modifier
            .width(120.dp)
            .height(2.dp)
            .padding(4.dp)
            .size(20.dp),

        value = desc.value,
        onValueChange = {
            if (descError.value) {
                descError.value = false
            }
            desc.value = it
        },
        isError = descError.value )
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DateToday(){
    val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy")
    val currentDateAndTime = sdf.format(Date())
    Text(text=currentDateAndTime)

}
@Composable
fun Satisfaction(
    @DrawableRes drawable: Int,
    //@StringRes text: Int,
    modifier: Modifier = Modifier
) {




    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(15.dp)
                .clip(CircleShape)

        )

    }
}
@Composable
fun SatisfactionElement(
    modifier: Modifier = Modifier.padding(5.dp)
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = modifier
    ) {
        items(satisfactionValue) { item ->
            Satisfaction(item.drawable)
        }
    }
}
private val satisfactionValue = listOf(
    R.drawable.a to R.string.a,
    R.drawable.b to R.string.b,
    R.drawable.c to R.string.c,
    R.drawable.d to R.string.d,
    R.drawable.e to R.string.e
).map { DrawableStringPair(it.first, it.second) }



@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun milestone(text: String){
    Card(
        elevation = 10.dp,
        border = BorderStroke(1.dp, color = Color.Black),
        modifier = Modifier.padding(10.dp)
    ){ Column(
        Modifier.verticalScroll(rememberScrollState())) {
        Text(text, modifier = Modifier.padding(16.dp))
        Spacer(Modifier.height(5.dp))
        Column() {

        MilestoneTitle(Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(37.dp))
        MileStoneDescription(Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(30.dp))}
        SatisfactionElement()
        DateToday()
        //satisfaction()

    }

    }

}
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun x(){
//    LazyRow( horizontalArrangement = Arrangement.spacedBy(8.dp),
//        contentPadding = PaddingValues(horizontal = 16.dp),
//        modifier = Modifier) {
//        items(2) {item->
//            milestone("Day 1")
//            milestone("Day 2")
//        }
//    }
    Row {
        milestone(text = "Day 1")
        milestone(text ="Day 2")
        milestone(text = "Day 3")
    }

}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun expandMoreContent(){



            Surface {
                x()
            }
        }




@Composable
fun expandLessContent(){
    Title()
    Description(true)

}


//@RequiresApi(Build.VERSION_CODES.N)
//@Composable
//fun ComposeDialogDemo() {
//    // State to manage if the alert dialog is showing or not.
//    // Default is false (not showing)
//    var (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
//    Column(
//        // Make the column fill the whole screen space (width and height).
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(15.dp),
//        // Place all children at center horizontally.
//        horizontalAlignment = Alignment.End,
//        // Place all children at center vertically.
//        verticalArrangement = Arrangement.Bottom
//    ) {
//        ExtendedFloatingActionButton(modifier = Modifier.padding(2.dp), text = { Text(text = "Goal") },
//            onClick = {
//                setShowDialog(true)
//
//            },
//            icon = { Icon(Filled.Add,"Goal") },
//            elevation = FloatingActionButtonDefaults.elevation(8.dp))
//        // Create alert dialog, pass the showDialog state to this Composable
//        DialogDemo(showDialog, setShowDialog)
//    }
//}
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Dateselect(){

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {

        // Creating a button that on
        // click displays/shows the DatePickerDialog
        Button(modifier = Modifier.padding(3.dp), onClick = {
            mDatePickerDialog.show()
        } ) {
            Text(text = "Open Date Picker", color = Color.White)
        }



        // Displaying the mDate value in the Text
        Text(modifier = Modifier.padding(3.dp),
            text = "Selected Date: ${mDate.value}", fontSize = 15.sp, textAlign = TextAlign.End)
    }

}


//@RequiresApi(Build.VERSION_CODES.N)
//@Composable
//fun DialogDemo(showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
//
//
//
//
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = {
//            },
//            title = {
//                Text("Title")
//
//
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        // Change the state to close the dialog
//                        setShowDialog(false)
//                    },
//                ) {
//                    Text("Done")
//                }
//            },
//            dismissButton = {
//                Button(
//                    onClick = {
//                        // Change the state to close the dialog
//                        setShowDialog(false)
//                    },
//                ) {
//                    Text("Cancel")
//                }
//            },
//            text = {
//                addGoal()
//
//            },
//        )
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.N)
//@Composable
//fun addGoal(){
//    var text by remember { mutableStateOf(TextFieldValue(""))}
//
//    Column(modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.SpaceEvenly) {
//        Title()
////        TextField(modifier = Modifier.padding(5.dp),value = text, onValueChange = { newtext ->
////            text = newtext
////
////
////        }
//        Description()
//
//
//
//        Text(text="Select time to achive that goal", modifier = Modifier.padding(5.dp))
//        Dateselect()
//
//
//    }
//}








@RequiresApi(Build.VERSION_CODES.N)
@Composable
private fun ShowFull() {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(12.dp)
//            .animateContentSize(
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                    stiffness = Spring.StiffnessLow
//                )

    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Column {
                Text(text = "Goal",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primaryVariant)

                    Title()


                Description(singleLine=true)
                Spacer(modifier = Modifier.padding(3.dp))
                Chips()

            }

            if (expanded) {

                expandMoreContent()

            }
        }
        IconButton(onClick = { expanded = !expanded }) {
           // Icon(
                //imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
               // contentDescription = if (expanded) {
                   // stringResource(R.string.show_less)
               // } else {
                   // stringResource(R.string.show_more)
                }


        }
    }







private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

//@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
//@Composable
//fun goalsCategoryValue () {
//
//    goalsCategoryValue (
//        drawable=R.drawable.loveandrelationship,
//        text=R.string.money,
//        modifier = Modifier.padding(8.dp)
//    )
//}