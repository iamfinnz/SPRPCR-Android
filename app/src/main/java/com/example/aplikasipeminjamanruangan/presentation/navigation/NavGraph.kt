package com.example.aplikasipeminjamanruangan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aplikasipeminjamanruangan.presentation.screen.HistoryScreen
import com.example.aplikasipeminjamanruangan.presentation.screen.SearchingScreen
import com.example.aplikasipeminjamanruangan.presentation.screen.SplashScreen
import com.example.aplikasipeminjamanruangan.presentation.screen.WaitingDetailScreen
import com.example.aplikasipeminjamanruangan.presentation.screen.WaitingScreen
import com.example.aplikasipeminjamanruangan.presentation.screen.home.HomeDetailScreen
import com.example.aplikasipeminjamanruangan.presentation.screen.home.HomeScreen
import com.example.aplikasipeminjamanruangan.presentation.screen.home.LendingFormScreen
import com.example.aplikasipeminjamanruangan.presentation.screen.home.LendingScreen
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.AppViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.CameraXViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.DosenViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.MataKuliahViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.PeminjamanViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.PengajuanViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.RetrofitViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.SharedViewModel

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    val appViewModel: AppViewModel = hiltViewModel()
    val sharedViewModel: SharedViewModel = viewModel()
    val cameraXViewModel: CameraXViewModel = hiltViewModel()
    val retrofitViewModel: RetrofitViewModel = hiltViewModel()
    val pengajuanViewModel: PengajuanViewModel = hiltViewModel()
    val peminjamanViewModel: PeminjamanViewModel = hiltViewModel()
    val mataKuliahViewModel: MataKuliahViewModel = hiltViewModel()
    val dosenViewModel: DosenViewModel = hiltViewModel()

    NavHost(
        navController = navController, startDestination = Splash.route, modifier = modifier
    ) {
        composable(route = Splash.route) {
            SplashScreen(navController = navController, modifier = modifier)
        }
        composable(route = Home.route) {
            HomeScreen(
                appViewModel = appViewModel,
                onHeadingToDetail = { room ->
                    sharedViewModel.addRooms(room)
                    navController.navigateSingleTopTo(HomeDetail.route)
                },
                onSearchRooms = {
                    navController.navigateSingleTopTo(HomeSearch.route)
                },
                modifier = modifier
            )
        }

        composable(route = HomeSearch.route) {
            SearchingScreen(
                onNavBack = {
                    navController.navigateSingleTopTo(HomeSearch.route)
                },
                onHeadingToDetail = {
                    sharedViewModel.addRooms(it)
                    navController.navigateSingleTopTo(HomeDetail.route)
                },
                pengajuanViewModel = pengajuanViewModel,
                mataKuliahViewModel = mataKuliahViewModel,
                appViewModel = appViewModel,
            )
        }

        composable(route = HomeDetail.route) {
            HomeDetailScreen(
                sharedViewModel = sharedViewModel,
                onNavBack = {
                    navController.navigateSingleTopTo(HomeSearch.route)
                },
                onLending = {
                    navController.navigate(Lending.route)
                },
                modifier = modifier
            )
        }

        composable(route = Lending.route) {
            LendingScreen(
                cameraXViewModel = cameraXViewModel,
                sharedViewModel = sharedViewModel,
                retrofitViewModel = retrofitViewModel,
                onNavBack = {
                    navController.popBackStack()
                },
                onNextPage = {
                    navController.navigateSingleTopTo(LendingForm.route)
                },
                modifier = modifier
            )
        }

        composable(route = LendingForm.route) {
            LendingFormScreen(
                dosenViewModel = dosenViewModel,
                sharedViewModel = sharedViewModel,
                retrofitViewModel = retrofitViewModel,
                pengajuanViewModel = pengajuanViewModel,
                onPinjamRuangan = { dataPengajuan, roomsUpdate ->
                    pengajuanViewModel.insertPengajuan(dataPengajuan)
                    appViewModel.updateRooms(roomsUpdate)
                },
                onSaveToHistory = {
//                    peminjamanViewModel.insertPeminjaman(it)
                },
                onNavBack = {
                    navController.popBackStack()
                },
                onActionClick = {
                    sharedViewModel.resetRoomsData()
                    retrofitViewModel.resetRetrofitData()
                    pengajuanViewModel.resetPengajuanViewModel()
                    navController.navigateSingleTopTo(HomeSearch.route)
                },
                modifier = modifier
            )
        }

        composable(route = WaitingList.route) {
            WaitingScreen(
                pengajuanViewModel = pengajuanViewModel,
                onHeadToDetailWaiting = {
                    sharedViewModel.addPengajuan(it)
                    navController.navigateSingleTopTo(WaitingDetail.route)
                }
            )
        }
        composable(route = WaitingDetail.route) {
            WaitingDetailScreen(
                sharedViewModel = sharedViewModel,
                onNavBack = { navController.popBackStack() },
            )
        }
        composable(route = History.route) {
            HistoryScreen(peminjamanViewModel = peminjamanViewModel)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}