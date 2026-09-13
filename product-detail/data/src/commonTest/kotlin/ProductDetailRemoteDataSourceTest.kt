import com.fajar.myproductcatalogapp.core.data.network.HttpException
import com.fajar.myproductcatalogapp.core.data.network.mapStatusCodeToError
import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.core.test_utils.TestDispatcherProvider
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.ProductDetailRemoteDataSource
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.MapperToDomain
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.ProductDetailRemoteDataSourceImpl
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.ProductDetailAPIService
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.dto.ProductDetailDto
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.dto.ProductReviewDto
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetail
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
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
import kotlin.test.assertNull

class ProductDetailRemoteDataSourceTest {

    private val mockedAPIService: ProductDetailAPIService = mock()
    private lateinit var dataSourceUnderTest: ProductDetailRemoteDataSource
    private val mapperToDomain = MapperToDomain()
    private val dispatcherProvider: TestDispatcherProvider =
        TestDispatcherProvider(TestCoroutineScheduler())

    @BeforeTest
    fun setup() = runTest(dispatcherProvider.testCoroutineScheduler) {
        dataSourceUnderTest = ProductDetailRemoteDataSourceImpl(
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
    fun `When getting product detail success, should return result success`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            val expectedProductId = 999L
            val stubReviewDate = "2025-04-30T09:41:02.053Z"

            val mockedResponseDto = ProductDetailDto(
                id = expectedProductId.toInt(),
                images = listOf("dummy.com/image.png"),
                overallRating = 4.0,
                description = "lorem ipsum",
                title = "Title Test",
                price = 100.0,
                discountPercentage = 10.0,
                reviews = List(3) {
                    ProductReviewDto(
                        date = stubReviewDate,
                        reviewerName = "Reviewer Test",
                        reviewerEmail = "reviewer@test.com",
                        rating = 5,
                        comment = "Review Comment Test",
                    )
                },
            )

            everySuspend {
                mockedAPIService.getProductDetail(productId = expectedProductId)
            } returns mockedResponseDto

            val result = dataSourceUnderTest
                .getProductDetail(productId = expectedProductId)

            verifySuspend(VerifyMode.exactly(1)) {
                mockedAPIService.getProductDetail(expectedProductId)
            }

            val expectedResultData = mapperToDomain
                .mapProductDetailDtoToDomain(mockedResponseDto)

            assertIs<Result.Success<ProductDetail>>(result)
            assertEquals(expectedResultData, result.data)
        }

    @Test
    fun `When getting product detail failed, should return result error`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            val expectedProductId = 999L
            val mockedHttpException = HttpException(500)

            everySuspend {
                mockedAPIService.getProductDetail(productId = expectedProductId)
            } throws mockedHttpException

            val result = dataSourceUnderTest
                .getProductDetail(productId = expectedProductId)

            verifySuspend(VerifyMode.exactly(1)) {
                mockedAPIService.getProductDetail(expectedProductId)
            }

            val expectedError = mapStatusCodeToError[mockedHttpException.statusCode]

            assertIs<Result.Error<DataError>>(result)
            assertEquals(expectedError, result.error)
        }

    @Test
    fun `When getting product detail success but with insufficient data, should return result error`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            val expectedProductId = 999L
            val mockedResponseDto = ProductDetailDto(
                id = null // deliberately set to have insufficient data
            )

            everySuspend {
                mockedAPIService.getProductDetail(productId = expectedProductId)
            } returns mockedResponseDto

            val result = dataSourceUnderTest
                .getProductDetail(productId = expectedProductId)

            verifySuspend(VerifyMode.exactly(1)) {
                mockedAPIService.getProductDetail(expectedProductId)
            }

            val expectedResultData = mapperToDomain
                .mapProductDetailDtoToDomain(mockedResponseDto)

            assertNull(expectedResultData)
            assertIs<Result.Error<DataError>>(result)
            assertIs<DataError>(result.error)
        }
}