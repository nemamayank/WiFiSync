package com.mayank.wifisync

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.work.WorkerParameters
import com.mayank.wifisync.presentation.ui.state.ApiResult
import com.mayank.wifisync.data.local.User
import com.mayank.wifisync.data.repository.UserRepositoryImpl
import com.mayank.wifisync.data.infrastructure.FetchUserWorker
import com.mayank.wifisync.domain.usecase.GetUsersUseCase
import com.mayank.wifisync.utils.WiFiMonitor
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import androidx.work.ListenableWorker.Result as WorkResult

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [28])
class FetchUserWorkerTest {
    @Mock
    private lateinit var mockContext: Context
    @Mock
    private lateinit var mockUsersUseCase: GetUsersUseCase
    @Mock
    private lateinit var mockParams: WorkerParameters
    @Mock
    private lateinit var wifiMonitorMock: MockedStatic<WiFiMonitor>
    @Mock
    private lateinit var mockConnectivityManager: ConnectivityManager
    @Mock
    private lateinit var mockNetwork: Network
    @Mock
    private lateinit var mockCapabilities: NetworkCapabilities

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        whenever(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(mockConnectivityManager)
        whenever(mockConnectivityManager.activeNetwork)
            .thenReturn(mockNetwork)
        whenever(mockConnectivityManager.getNetworkCapabilities(mockNetwork))
            .thenReturn(mockCapabilities)
        whenever(mockCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(true)
        whenever(mockCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            .thenReturn(true)

        wifiMonitorMock.`when`<Boolean> {
            WiFiMonitor.isWifiConnected(mockContext)
        }.thenReturn(true)

        startKoin {
            modules(module {
                single { mockUsersUseCase }
            })
        }
    }

    @After
    fun tearDown() {
        wifiMonitorMock.close()
        stopKoin()
        clearInvocations(mockUsersUseCase)
    }

    @Test
    fun `doWork should retry refreshUsers 3 times and return success`() = runTest {
        val fakeUserList = listOf(
            User(1,
                "mock@emali.com",
                "mockUser", "mockLastName",
                "mockAvatar",
                System.currentTimeMillis())
        )

        whenever(mockUsersUseCase.refreshUsers())
            .thenReturn(ApiResult.Error("Fetch failed, Retrying"))
            .thenReturn(ApiResult.Error("Fetch failed, Retrying"))
            .thenReturn(ApiResult.Success(fakeUserList, "Data fetched successfully"))

        val worker = FetchUserWorker(mockContext, mockParams)
        val result = worker.doWork()

        verify(mockUsersUseCase, times(3)).refreshUsers()
        assertEquals(WorkResult.success(), result)
    }
}