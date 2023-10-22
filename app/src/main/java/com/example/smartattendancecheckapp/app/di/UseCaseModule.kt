package com.example.smartattendancecheckapp.app.di

import com.example.smartattendancecheckapp.domain.repo.AttendCheckRepository
import com.example.smartattendancecheckapp.domain.repo.CalendarCheckRepository
import com.example.smartattendancecheckapp.domain.repo.EnrollFaceRepository
import com.example.smartattendancecheckapp.domain.repo.LoginRepository
import com.example.smartattendancecheckapp.domain.repo.SignUpRepository
import com.example.smartattendancecheckapp.domain.repo.TestRepository
import com.example.smartattendancecheckapp.domain.usecase.LoadAttendCheckUseCase
import com.example.smartattendancecheckapp.domain.usecase.LoadCalendarCheckUseCase
import com.example.smartattendancecheckapp.domain.usecase.LoadTestListUseCase
import com.example.smartattendancecheckapp.domain.usecase.RequestEnrollFaceUseCase
import com.example.smartattendancecheckapp.domain.usecase.RequestLoginUseCase
import com.example.smartattendancecheckapp.domain.usecase.RequestSignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideTestListUseCase(repo: TestRepository): LoadTestListUseCase {
        return LoadTestListUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(repo: LoginRepository): RequestLoginUseCase {
        return RequestLoginUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideSignUpUseCase(repo: SignUpRepository): RequestSignUpUseCase {
        return RequestSignUpUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideEnrollFaceUseCase(repo: EnrollFaceRepository): RequestEnrollFaceUseCase {
        return RequestEnrollFaceUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideAttendCheckUseCase(repo: AttendCheckRepository): LoadAttendCheckUseCase {
        return LoadAttendCheckUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideCalendarCheckUseCase(repo: CalendarCheckRepository): LoadCalendarCheckUseCase {
        return LoadCalendarCheckUseCase(repo)
    }
}