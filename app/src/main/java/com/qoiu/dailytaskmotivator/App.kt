package com.qoiu.dailytaskmotivator

import android.app.Application
import android.content.Context
import com.qoiu.dailytaskmotivator.data.category.CategoryDataSource
import com.qoiu.dailytaskmotivator.data.category.CategoryDbToCategoryMapper
import com.qoiu.dailytaskmotivator.data.category.CategoryRepository
import com.qoiu.dailytaskmotivator.data.category.CategoryToCategoryDbMapper
import com.qoiu.dailytaskmotivator.data.task.TaskDataSource
import com.qoiu.dailytaskmotivator.data.task.TaskDbToTaskMapper
import com.qoiu.dailytaskmotivator.data.task.TaskRepository
import com.qoiu.dailytaskmotivator.data.task.TaskToTaskDbMapper
import com.qoiu.dailytaskmotivator.domain.CategoriesInteractor
import com.qoiu.dailytaskmotivator.domain.TaskInteractor
import com.qoiu.dailytaskmotivator.presentation.CategoryToStructureMapper
import com.qoiu.dailytaskmotivator.presentation.StructureToCategoryMapper
import com.qoiu.dailytaskmotivator.presentation.StructureToTaskMapper
import com.qoiu.dailytaskmotivator.presentation.TaskToStructureMapper
import com.qoiu.dailytaskmotivator.presentation.main.MainViewModel
import com.qoiu.dailytaskmotivator.presentation.shop.ShopModel
import com.qoiu.dailytaskmotivator.presentation.task.TaskModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        init(this)
    }

    fun init(context: Context) {
        val myModule = module {
            single <ResourceProvider.SharedProvider>{ ResourceProvider.Shared(context) }
            single <ResourceProvider.StringProvider>{ ResourceProvider.String(context) }
            single <RealmProvider>{ RealmProvider.Base(context) }
            single { TaskDataSource(get()) }
            single { CategoryDataSource(get()) }
            single { TaskRepository(get(TaskDataSource::class), TaskDbToTaskMapper(), TaskToTaskDbMapper()) }
            single {
                CategoryRepository(
                    get(CategoryDataSource::class), CategoryDbToCategoryMapper(),
                    CategoryToCategoryDbMapper()
                )
            }
            single<TaskInteractor> { TaskInteractor.Base(get(TaskRepository::class), get()) }
            single<CategoriesInteractor> { CategoriesInteractor.Base(get(CategoryRepository::class)) }
            viewModel { MainViewModel(Communication.Base(), get()) }
            viewModel { TaskModel(get(),get(),
                CategoryToStructureMapper(), TaskToStructureMapper(get()),
                StructureToTaskMapper(get()), StructureToCategoryMapper(), get()) }
            viewModel { ShopModel() }
        }
        startKoin {
            androidContext(context)
            modules(myModule)
        }
    }
}