import com.fajar.myproductcatalogapp.core.data.network.HttpException
import com.fajar.myproductcatalogapp.core.data.network.mapStatusCodeToError
import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Page
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.core.test_utils.TestDispatcherProvider
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.ProductPreviewsRemoteDataSource
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.MapperToDomain
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.ProductPreviewsRemoteDataSourceImpl
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.ProductPreviewsAPIService
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto.ListProductPreviewsDto
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto.ProductsPreviewItemDto
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifyNoMoreCalls
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ProductReviewsRemoteDataSourceTest {

    private val mockedAPIService: ProductPreviewsAPIService = mock()
    private lateinit var dataSourceUnderTest: ProductPreviewsRemoteDataSource
    private val mapperToDomain = MapperToDomain()
    private val dispatcherProvider: TestDispatcherProvider =
        TestDispatcherProvider(TestCoroutineScheduler())

    @BeforeTest
    fun setup() = runTest(dispatcherProvider.testCoroutineScheduler) {
        dataSourceUnderTest = ProductPreviewsRemoteDataSourceImpl(
            apiService = mockedAPIService,
            dispatcherProvider = dispatcherProvider,
            mapperToDomain = MapperToDomain()
        )
    }

    @AfterTest
    fun tearDown() {
        verifyNoMoreCalls(mockedAPIService)
    }

    @Test
    fun `When getting list product previews success, should return result success`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            // [Setup Mock]
            var expectedQuery = ""
            val expectedSize = 10
            val expectedTotal = 5
            val expectedOffset = 20

            val mockedResponseDto = ListProductPreviewsDto(
                total = expectedTotal,
                limit = expectedSize,
                skip = expectedOffset,
                products = List(expectedSize) {
                    ProductsPreviewItemDto(
                        id = it,
                        thumbnail = "dummy.com/thumbail.png",
                        title = "TitleTest",
                        discountPercentage = 10.0,
                        price = 100.0,
                    )
                }
            )

            everySuspend {
                mockedAPIService
                    .getRandomProductPreviews(
                        any(), any()
                    )
            } returns mockedResponseDto

            everySuspend {
                mockedAPIService
                    .getProductPreviewsByQuery(
                        any(), any(), any()
                    )
            } returns mockedResponseDto


            // [Sub-Test Scenario : When query is blank]
            var result = dataSourceUnderTest.getProductPreviews(
                query = expectedQuery,
                offset = expectedOffset,
                size = expectedSize
            )

            verifySuspend(VerifyMode.exactly(1)) {
                mockedAPIService.getRandomProductPreviews(
                    limit = expectedSize,
                    skip = expectedOffset
                )
            }

            verifySuspend(VerifyMode.exactly(0)) {
                mockedAPIService.getProductPreviewsByQuery(
                    query = expectedQuery,
                    limit = expectedSize,
                    skip = expectedOffset
                )
            }

            var expectedResultData = mapperToDomain
                .mapListProductPreviewsDtoToDomain(mockedResponseDto)

            assertIs<Result.Success<Page<ProductPreviewItem>>>(result)
            assertEquals(expectedResultData, result.data)
            assertEquals(expectedSize, result.data.pagedList.size)


            // [Sub-Test Scenario : When query is not blank]
            expectedQuery = "Headset"
            result = dataSourceUnderTest.getProductPreviews(
                query = expectedQuery,
                offset = expectedOffset,
                size = expectedSize
            )

            verifySuspend(VerifyMode.exactly(0)) {
                mockedAPIService.getRandomProductPreviews(
                    limit = expectedSize,
                    skip = expectedOffset
                )
            }

            verifySuspend(VerifyMode.exactly(1)) {
                mockedAPIService.getProductPreviewsByQuery(
                    query = expectedQuery,
                    limit = expectedSize,
                    skip = expectedOffset
                )
            }

            expectedResultData = mapperToDomain
                .mapListProductPreviewsDtoToDomain(mockedResponseDto)

            assertIs<Result.Success<Page<ProductPreviewItem>>>(result)
            assertEquals(expectedResultData, result.data)
            assertEquals(expectedSize, result.data.pagedList.size)
        }

    @Test
    fun `When getting list product previews failed, should return result error`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            // [Setup Mock]
            var expectedQuery = ""
            val stubSize = 10
            val stubOffset = 20

            val mockedHttpException = HttpException(500)

            everySuspend {
                mockedAPIService
                    .getRandomProductPreviews(
                        any(), any()
                    )
            } throws mockedHttpException

            everySuspend {
                mockedAPIService
                    .getProductPreviewsByQuery(
                        any(), any(), any()
                    )
            } throws mockedHttpException


            // [Sub-Test Scenario : When query is blank]
            var result = dataSourceUnderTest.getProductPreviews(
                query = expectedQuery,
                offset = stubOffset,
                size = stubSize
            )

            verifySuspend(VerifyMode.exactly(1)) {
                mockedAPIService.getRandomProductPreviews(
                    limit = stubSize,
                    skip = stubOffset
                )
            }

            verifySuspend(VerifyMode.exactly(0)) {
                mockedAPIService.getProductPreviewsByQuery(
                    query = expectedQuery,
                    limit = stubSize,
                    skip = stubOffset
                )
            }

            var expectedError = mapStatusCodeToError[mockedHttpException.statusCode]

            assertIs<Result.Error<DataError>>(result)
            assertEquals(expectedError, result.error)


            // [Sub-Test Scenario : When query is not blank]
            expectedQuery = "Headset"
            result = dataSourceUnderTest.getProductPreviews(
                query = expectedQuery,
                offset = stubOffset,
                size = stubSize
            )

            verifySuspend(VerifyMode.exactly(0)) {
                mockedAPIService.getRandomProductPreviews(
                    limit = stubSize,
                    skip = stubOffset
                )
            }

            verifySuspend(VerifyMode.exactly(1)) {
                mockedAPIService.getProductPreviewsByQuery(
                    query = expectedQuery,
                    limit = stubSize,
                    skip = stubOffset
                )
            }

            expectedError = mapStatusCodeToError[mockedHttpException.statusCode]
            assertIs<Result.Error<DataError>>(result)
            assertEquals(expectedError, result.error)
        }

    @Test
    fun `When getting list product previews success but some of items has insufficient data, should filtered them out`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            // [Setup Mock]
            var expectedQuery = ""
            val expectedItemSizeWithSufficientData = 5
            val expectedItemSizeWithInsufficientData = 5
            val totalItems =
                expectedItemSizeWithSufficientData + expectedItemSizeWithInsufficientData
            val expectedTotal = 100
            val expectedOffset = 20

            val mockedResponseDto = ListProductPreviewsDto(
                total = expectedTotal,
                limit = totalItems,
                skip = expectedOffset,
                products = List(expectedItemSizeWithInsufficientData) {
                    ProductsPreviewItemDto(
                        id = null // deliberately set to have insufficient data
                    )
                } + List(expectedItemSizeWithSufficientData) {
                    ProductsPreviewItemDto(
                        id = it,
                        thumbnail = "dummy.com/thumbail.png",
                        title = "TitleTest",
                        discountPercentage = 10.0,
                        price = 100.0,
                    )
                }
            )

            everySuspend {
                mockedAPIService
                    .getRandomProductPreviews(
                        any(), any()
                    )
            } returns mockedResponseDto

            everySuspend {
                mockedAPIService
                    .getProductPreviewsByQuery(
                        any(), any(), any()
                    )
            } returns mockedResponseDto


            // [Sub-Test Scenario : When query is blank]
            var result = dataSourceUnderTest.getProductPreviews(
                query = expectedQuery,
                offset = expectedOffset,
                size = expectedItemSizeWithSufficientData
            )

            verifySuspend(VerifyMode.exactly(1)) {
                mockedAPIService.getRandomProductPreviews(
                    limit = expectedItemSizeWithSufficientData,
                    skip = expectedOffset
                )
            }

            verifySuspend(VerifyMode.exactly(0)) {
                mockedAPIService.getProductPreviewsByQuery(
                    query = expectedQuery,
                    limit = expectedItemSizeWithSufficientData,
                    skip = expectedOffset
                )
            }

            var expectedResultData = mapperToDomain
                .mapListProductPreviewsDtoToDomain(mockedResponseDto)

            assertIs<Result.Success<Page<ProductPreviewItem>>>(result)
            assertEquals(expectedResultData, result.data)
            assertEquals(expectedItemSizeWithSufficientData, result.data.pagedList.size)

            // [Sub-Test Scenario : When query is not blank]
            expectedQuery = "Headset"
            result = dataSourceUnderTest.getProductPreviews(
                query = expectedQuery,
                offset = expectedOffset,
                size = expectedItemSizeWithSufficientData
            )

            verifySuspend(VerifyMode.exactly(0)) {
                mockedAPIService.getRandomProductPreviews(
                    limit = expectedItemSizeWithSufficientData,
                    skip = expectedOffset
                )
            }

            verifySuspend(VerifyMode.exactly(1)) {
                mockedAPIService.getProductPreviewsByQuery(
                    query = expectedQuery,
                    limit = expectedItemSizeWithSufficientData,
                    skip = expectedOffset
                )
            }

            expectedResultData = mapperToDomain
                .mapListProductPreviewsDtoToDomain(mockedResponseDto)

            assertIs<Result.Success<Page<ProductPreviewItem>>>(result)
            assertEquals(expectedResultData, result.data)
            assertEquals(expectedItemSizeWithSufficientData, result.data.pagedList.size)
        }
}