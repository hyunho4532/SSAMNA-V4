package com.app.presentation.ui.main.home.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.app.domain.model.dto.ShowdownDTO
import com.app.domain.model.dto.ShowdownInviteDTO
import com.app.domain.model.entry.PolygonBoxItem
import com.app.domain.model.user.User
import com.app.presentation.R
import com.app.presentation.component.box.polygon.PolygonBox
import com.app.presentation.component.dialog.ChallengeBottomSheet
import com.app.presentation.component.dialog.ShowChallengeDetailDialog
import com.app.presentation.component.tool.Spacer
import com.app.presentation.component.tool.activateCard
import com.app.presentation.component.tool.challengeRegistrationCard
import com.app.presentation.component.util.calculatorActivateCardWeight
import com.app.presentation.component.util.responsive.setUpWidth
import com.app.domain.model.enum.CardType
import com.app.domain.model.state.ChallengeMaster
import com.app.presentation.component.admob.Banner
import com.app.presentation.component.dialog.ShowdownDialog
import com.app.presentation.component.tool.showdownSelectCard
import com.app.presentation.viewmodel.ActivityLocationViewModel
import com.app.presentation.viewmodel.ChallengeViewModel
import com.app.presentation.viewmodel.CrewViewModel
import com.app.presentation.viewmodel.ShowdownViewModel
import com.app.presentation.viewmodel.StateViewModel
import com.app.presentation.viewmodel.UserViewModel
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController(),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    challengeViewModel: ChallengeViewModel = hiltViewModel(),
    crewViewModel: CrewViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    showdownViewModel: ShowdownViewModel = hiltViewModel(),
    userList: State<User>,
    context: Context,
    stateViewModel: StateViewModel
) {
    val activateData  = activityLocationViewModel.activateData.collectAsState()
    val challengeData = challengeViewModel.challengeData.collectAsState()
    val challengeDetailData = challengeViewModel.challengeDetailData.collectAsState()
    val crewData = crewViewModel.crew.collectAsState()

    /**
     * googleId를 전역 변수로 선언한다.
     */
    val googleId = userViewModel.getSavedLoginState()

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    /**
     * 갤러리에서 이미지를 선택하는 런처
     */
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            /**
             * 이미지를 선택하면 user 테이블에 프로필 이미지를 업데이트한다.
             */
            selectedImageUri = uri
            userViewModel.updateProfileUrl(
                googleId = googleId,
                profileUrl = selectedImageUri.toString()
            )
        }
    )

    // Custom ImageLoader 설정
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }

    val challengeMaster = remember {
        mutableStateListOf<ChallengeMaster>()
    }

    val showdown = remember {
        mutableStateListOf<ShowdownDTO>()
    }

    val showdownInvite = remember {
        mutableStateListOf<ShowdownInviteDTO>()
    }

    var sumCount by remember {
        mutableIntStateOf(0)
    }

    var sumKcal by remember {
        mutableDoubleStateOf(0.0)
    }

    var sumKm by remember {
        mutableDoubleStateOf(0.0)
    }

    val showChallengeBottomSheet = remember {
        mutableStateOf(false)
    }

    val showChallengeDialogPopup = remember {
        mutableStateOf(false)
    }

    /**
     * 대결 신청 팝업
     */
    val showdownDialogPopup = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    LaunchedEffect(key1 = Unit) {
        val challengeMasterAll = challengeViewModel.selectChallengeAll()
        challengeMaster.addAll(challengeMasterAll)

        /**
         * 대결 초대 관련 테이블에 있는 데이터를 조회한다.
         */
        val showdownInviteMaster = showdownViewModel.select(userId = googleId)
        showdownInvite.addAll(showdownInviteMaster)

        /**
         * 대결 테이블에 있는 데이터를 조회한다.
         */
        val showdownMaster = showdownViewModel.showdownSelect(userId = googleId)
        showdown.addAll(showdownMaster)

        selectedImageUri = userViewModel.selectProfileUrl(googleId)?.toUri()

        activityLocationViewModel.selectActivityFindByGoogleId(userList.value.id)

        challengeViewModel.selectChallengeByGoogleId(googleId = googleId)
        crewViewModel.crewFindById(googleId = googleId)
    }

    LaunchedEffect(key1 = activateData.value) {
        if (activateData.value.isNotEmpty()) {
            sumCount = activateData.value.sumOf {
                it.cul["goal_count"]?.jsonPrimitive?.int ?: 0
            }
            sumKcal = activateData.value.sumOf {
                it.cul["kcal_cul"]?.jsonPrimitive?.double ?: 0.0
            }
            sumKm = activateData.value.sumOf {
                it.cul["km_cul"]?.jsonPrimitive?.double ?: 0.0
            }
        }
    }

    val polygonBoxItems = listOf(
        PolygonBoxItem("걸음 수", sumCount),
        PolygonBoxItem("칼로리", sumKcal),
        PolygonBoxItem("km", sumKm)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp, start = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        /**
         * 프로필의 이미지와 이름을 조회한다.
         */
        Column(
            modifier = Modifier
                .width(setUpWidth()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(selectedImageUri)
                        .placeholder(R.drawable.default_user)
                        .error(R.drawable.default_user)
                        .build(),
                    imageLoader = imageLoader
                ),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(78.dp)
                    .clip(CircleShape)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
            )

            Spacer(width = 0.dp, height = 8.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = userList.value.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(width = 4.dp, height = 0.dp)

                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "사용자 설정 아이콘",
                    modifier = Modifier.clickable {
                        val userJson = Json.encodeToString(userList.value)
                        navController.navigate("settings/$userJson")
                    },
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Image(
                    painter = painterResource(R.drawable.showdown),
                    contentDescription = "대결 조회 아이콘",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            showdownDialogPopup.value = true
                        }
                )
            }
        }

        /**
         * Polygon를 이용하여 걸음 수, 칼로리, km를 조회한다.
         */
        Row (
            modifier = Modifier
                .width(setUpWidth())
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            polygonBoxItems.forEach { item ->
                PolygonBox(
                    title = item.title,
                    data = item.data,
                    stateViewModel = stateViewModel
                )
            }
        }

        /**
         * 현재 사용자의 활동과 활동 갯수, 전체 활동 화면이 이동되는 아이콘을 조회한다.
         */
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, end = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "활동 (${activateData.value.size})",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Image(
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.navigate("activate")
                    },
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = "활동 아이콘",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }

        /**
         * 활동 내역을 조회한다.
         */
        Column (
            modifier = Modifier
                .height(
                    calculatorActivateCardWeight(
                        data = activateData,
                        minHeight = 160,
                        maxHeight = 320
                    )
                )
                .verticalScroll(rememberScrollState())
        ) {
            activateData.value.forEach { activateDTO ->
                activateCard(
                    height = 160.dp,
                    activateDTO = activateDTO,
                    cardType = CardType.ActivateStatus.Activity,
                    navController = navController
                )
            }
        }

        Spacer(width = 0.dp, height = 46.dp)

        /**
         * 현재 사용자의 활동과 활동 갯수, 전체 활동 화면이 이동되는 아이콘을 조회한다.
         */
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, end = 18.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    navController.navigate("showdownAuth")
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "대결 (${showdown.count()})",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Image(
                modifier = Modifier
                    .size(28.dp),
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "추가 아이콘",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }

        /**
         * 대결 내역을 조회한다.
         */
        Column {
            showdown.forEach { sd ->
                showdownSelectCard(
                    height = 140.dp,
                    data = sd
                )
            }
        }

        Spacer(width = 0.dp, height = 46.dp)

        /**
         * 현재 사용자의 크루와 크루 갯수, 크루 등록 화면으로 이동되는 아이콘을 조회한다.
         */
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 18.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    navController.navigate("crew")
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "크루",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Image(
                modifier = Modifier
                    .size(28.dp),
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "추가 아이콘",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }

        /**
         * 크루 내역을 조회한다.
         */
        Column (
            modifier = Modifier
                .width(setUpWidth())
                .height(116.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Row {
                crewData.value.forEachIndexed { index, crewItem ->
                    val imageName = crewItem.picture.replace("R.drawable.", "")
                    val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

                    Box(
                        modifier = Modifier
                            .padding(
                                top = 6.dp,
                                start = if (index == 0) 0.dp else 18.dp
                            )
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = rememberRipple(
                                    color = Color.Gray,
                                    bounded = true
                                )
                            ) {
                                val crewList = listOf(crewItem)
                                val crew = Uri.encode(Gson().toJson(crewList))
                                navController.navigate("crewDetail/$crew")
                            },
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = imageResId),
                                contentDescription = "크루 아이콘",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(86.dp)
                                    .clip(CircleShape),
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 4.dp),
                                text = crewItem.title,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(width = 0.dp, height = 46.dp)

        /**
         * 현재 사용자의 챌린지와 챌린지 갯수, 챌린지 등록 바텀 팝업창으로 이동되는 아이콘을 조회한다.
         */
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 18.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    showChallengeBottomSheet.value = true
                },
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "챌린지 (${challengeData.value.size})",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Image(
                modifier = Modifier
                    .size(28.dp),
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "추가 아이콘",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }

        /**
         * 챌린지 내역을 조회한다.
         */
        Column (
            modifier = Modifier
                .height(
                    calculatorActivateCardWeight(
                        data = challengeData,
                        minHeight = 80,
                        maxHeight = 160
                    )
                )
                .verticalScroll(rememberScrollState())
        ) {
            challengeData.value.forEach { challengeDTO ->
                challengeRegistrationCard(
                    challengeDTO = challengeDTO,
                    height = 80.dp,
                    sumKm = sumKm.toFloat(),
                    sumCount = sumCount
                ) { isPopup ->
                    challengeViewModel.selectChallengeById(challengeDTO.id) {
                        showChallengeDialogPopup.value = isPopup
                    }
                }
            }
        }

        Spacer(width = 0.dp, height = 24.dp)

        Column(
            modifier = Modifier.width(setUpWidth()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Banner()
        }
    }

    if (showChallengeDialogPopup.value) {
        if (challengeDetailData.value.isNotEmpty()) {
            ShowChallengeDetailDialog(
                isShowChallengePopup = showChallengeDialogPopup,
                challengeDetailData = challengeDetailData.value,
                sumKm = sumKm,
                sumCount = sumCount
            )
        }
    }

    if (showdownDialogPopup.value) {
        ShowdownDialog(
            isShowdownPopup = showdownDialogPopup,
            showdownInvite = showdownInvite
        )
    }

    if (showChallengeBottomSheet.value) {
        ChallengeBottomSheet(
            showBottomSheet = showChallengeBottomSheet,
            sheetState = sheetState,
            challengeMaster = challengeMaster,
            stateViewModel = stateViewModel
        )
    }
}