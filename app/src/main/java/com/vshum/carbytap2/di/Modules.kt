package com.vshum.carbytap2.di

import com.vshum.carbytap2.ui.FragmentCarTrajectory
import com.vshum.carbytap2.ui.PathPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module


object Modules {

    //модуль экрана с машинкой
    val carTrajectoryWindow = module {
        scope<FragmentCarTrajectory> {
            scoped<PathPresenter>(qualifier = named(Scopes.PATH_PRESENTER)) {
                PathPresenter()
            }
        }
    }
}