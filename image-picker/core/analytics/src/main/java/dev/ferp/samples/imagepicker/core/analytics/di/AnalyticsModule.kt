package dev.ferp.samples.imagepicker.core.analytics.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ferp.samples.imagepicker.core.analytics.AddProfilePicAnalyticsLogger
import dev.ferp.samples.imagepicker.core.analytics.AddProfilePicLogger
import dev.ferp.samples.imagepicker.core.analytics.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AnalyticsModule {

    companion object {
        @Singleton
        @Provides
        fun provideFirebaseAnalytics(): FirebaseAnalytics =
            Firebase.analytics.apply {
                @Suppress("KotlinConstantConditions")
                setAnalyticsCollectionEnabled(BuildConfig.ANALYTICS_ENABLED)
            }
    }

    @Binds
    abstract fun bindAddProfilePicLogger(
        impl: AddProfilePicAnalyticsLogger
    ): AddProfilePicLogger
}
