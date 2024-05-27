package com.hafidrf.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import com.hafidrf.app.core.shared.api.RetrofitServices
import com.hafidrf.app.data.local.CustomRoomDatabase
import com.hafidrf.app.data.remote.domain.PostServices
import com.hafidrf.app.data.remote.domain.UserServices
import com.hafidrf.app.data.repository.PostRepository
import com.hafidrf.app.data.repository.UserRepository
import com.hafidrf.app.ui.base.BaseUseCase
import com.hafidrf.app.ui.base.BaseViewModel
import com.hafidrf.app.ui.main.child.like.LikeUseCase
import com.hafidrf.app.ui.main.child.like.LikeViewModel
import com.hafidrf.app.ui.main.child.post.PostUseCase
import com.hafidrf.app.ui.main.child.post.PostViewModel
import com.hafidrf.app.ui.main.child.user.UserUseCase
import com.hafidrf.app.ui.main.child.user.UserViewModel
import com.hafidrf.app.ui.user.DetailUserUseCase
import com.hafidrf.app.ui.user.DetailUserViewModel


val localModule = module {
    single { CustomRoomDatabase.getDatabase(get()).postDao() }
}

val networkModule = module {
    single { RetrofitServices.endpointAPI<UserServices>(get()) }
    single { RetrofitServices.endpointAPI<PostServices>(get()) }
}

val dataSourceModule = module {
    single { UserRepository(get()) }
    single { PostRepository(get(), get()) }
}

val useCaseModule = module {
    single { BaseUseCase() }
    single { UserUseCase(get()) }
    single { PostUseCase(get()) }
    single { DetailUserUseCase(get(), get()) }
    single { LikeUseCase(get()) }
}

val viewModelModule = module {
    viewModel { BaseViewModel() }
    viewModel { UserViewModel(get()) }
    viewModel { PostViewModel(get()) }
    viewModel { DetailUserViewModel(get(), get()) }
    viewModel { LikeViewModel(get()) }
}


val appComponent: List<Module> = listOf(
    dataSourceModule,
    networkModule,
    viewModelModule,
    useCaseModule,
    localModule,
)