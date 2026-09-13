import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Rating
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.core.domain.model.RootNetworkError
import com.fajar.myproductcatalogapp.core.test_utils.TestDispatcherProvider
import com.fajar.myproductcatalogapp.product_detail.data.ProductDetailRepositoryImpl
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.ProductDetailRemoteDataSource
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetail
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetailRepository
import com.fajar.myproductcatalogapp.product_detail.domain.ProductReview
import dev.mokkery.answering.returns
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
import kotlin.time.Clock

class ProductDetailRepositoryTest {

    private lateinit var repositoryUnderTest: ProductDetailRepository
    private val mockedDataSource: ProductDetailRemoteDataSource = mock()

    private val dispatcherProvider: TestDispatcherProvider =
        TestDispatcherProvider(TestCoroutineScheduler())


    @BeforeTest
    fun setup() = runTest(dispatcherProvider.testCoroutineScheduler) {
        repositoryUnderTest = ProductDetailRepositoryImpl(
            remoteDataSource = mockedDataSource
        )
    }

    @AfterTest
    fun tearDown() = runTest {
        verifyNoMoreCalls(mockedDataSource)
    }

    @Test
    @OptIn(kotlin.time.ExperimentalTime::class)
    fun `getting product detail should return success, when data source return result success`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            val expectedProductId = 999L

            val expectedResultData = ProductDetail(
                id = expectedProductId,
                title = "Headset Test",
                description = "Lorem ipsum description test",
                price = 100.0,
                discountPercentage = 10.0,
                overallRating = Rating(
                    relativeScore = 4.5,
                    maxScore = 5.0
                ),
                imageUrls = listOf("dummy.com/image.png"),
                reviews = List(3) {
                    ProductReview(
                        rating = Rating(
                            relativeScore = 4.8,
                            maxScore = 5.0
                        ),
                        comment = "Lorem ipsum comment test",
                        postedTimestamp = Clock.System.now().toEpochMilliseconds(),
                        reviewerName = "Reviewer Test",
                        reviewerEmail = "reviewer@test.com"
                    )
                }
            )

            everySuspend {
                mockedDataSource.getProductDetail(any())
            } returns Result.Success(expectedResultData)

            val result = repositoryUnderTest
                .getProductDetail(productId = expectedProductId)

            verifySuspend(VerifyMode.exactly(1)) {
                mockedDataSource.getProductDetail(productId = expectedProductId)
            }

            assertIs<Result.Success<ProductDetail>>(result)
            assertEquals(expectedResultData, result.data)
        }

    @Test
    fun `getting product detail should return error, when data source return result error`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            val expectedProductId = 999L
            val expectedError = RootNetworkError.UNEXPECTED_ERROR

            everySuspend {
                mockedDataSource.getProductDetail(any())
            } returns Result.Error(expectedError)

            val result = repositoryUnderTest
                .getProductDetail(productId = expectedProductId)

            verifySuspend(VerifyMode.exactly(1)) {
                mockedDataSource.getProductDetail(productId = expectedProductId)
            }

            assertIs<Result.Error<DataError>>(result)
            assertEquals(expectedError, result.error)
        }
}