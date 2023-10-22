package com.example.smartattendancecheckapp.app.di

import com.example.smartattendancecheckapp.data.repoImpl.AttendCheckRepositoryImpl
import com.example.smartattendancecheckapp.data.repoImpl.EnrollFaceRepositoryImpl
import com.example.smartattendancecheckapp.data.repoImpl.LoginRepositoryImpl
import com.example.smartattendancecheckapp.data.repoImpl.SignUpRepositoryImpl
import com.example.smartattendancecheckapp.data.repoImpl.TestRepositoryImpl
import com.example.smartattendancecheckapp.domain.repo.AttendCheckRepository
import com.example.smartattendancecheckapp.domain.repo.EnrollFaceRepository
import com.example.smartattendancecheckapp.domain.repo.LoginRepository
import com.example.smartattendancecheckapp.domain.repo.SignUpRepository
import com.example.smartattendancecheckapp.domain.repo.TestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTestRepo(repoImp: TestRepositoryImpl): TestRepository

    @Binds
    abstract fun bindLoginRepo(repoImp: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindSignUpRepo(repoImp: SignUpRepositoryImpl): SignUpRepository

    @Binds
    abstract fun bindEnrollFaceRepo(repoImp: EnrollFaceRepositoryImpl): EnrollFaceRepository

    @Binds
    abstract fun bindAttendCheckRepo(repoImp: AttendCheckRepositoryImpl): AttendCheckRepository
}