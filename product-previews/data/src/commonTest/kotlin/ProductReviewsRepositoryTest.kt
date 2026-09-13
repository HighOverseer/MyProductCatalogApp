import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Page
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.core.domain.model.RootNetworkError
import com.fajar.myproductcatalogapp.core.test_utils.TestDispatcherProvider
import com.fajar.myproductcatalogapp.product_previews.data.ProductPreviewsRepositoryImpl
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.ProductPreviewsRemoteDataSource
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewsRepository
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

class ProductReviewsRepositoryTest {

    private lateinit var repositoryUnderTest: ProductPreviewsRepository
    private val mockedDataSource: ProductPreviewsRemoteDataSource = mock()

    private val dispatcherProvider: TestDispatcherProvider =
        TestDispatcherProvider(TestCoroutineScheduler())


    @BeforeTest
    fun setup() = runTest(dispatcherProvider.testCoroutineScheduler) {
        repositoryUnderTest = ProductPreviewsRepositoryImpl(
            remoteDataSource = mockedDataSource
        )
    }

    @AfterTest
    fun tearDown() = runTest {
        verifyNoMoreCalls(mockedDataSource)
    }

    @Test
    fun `getting list product previews should return success, when data source return result success`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            val expectedQuery = ""
            val expectedSize = 10
            val expectedOffset = 20

            val expectedResultData = Page(
                pagedList = List(expectedSize) {
                    ProductPreviewItem(
                        id = it.toLong(),
                        title = "Title Test",
                        thumbnailImageUrl = "dummy.com/image.png",
                        price = 100.0,
                        discountPercentage = 10.0
                    )
                },
                hasNext = true
            )

            everySuspend {
                mockedDataSource
                    .getProductPreviews(
                        any(), any(), any()
                    )
            } returns Result.Success(expectedResultData)

            val result = repositoryUnderTest
                .getProductPreviews(
                    query = expectedQuery,
                    size = expectedSize,
                    offset = expectedOffset
                )

            verifySuspend(VerifyMode.exactly(1)) {
                mockedDataSource
                    .getProductPreviews(
                        query = expectedQuery,
                        size = expectedSize,
                        offset = expectedOffset,
                    )
            }

            assertIs<Result.Success<Page<ProductPreviewItem>>>(result)
            assertEquals(expectedResultData, result.data)
        }

    @Test
    fun `getting list product previews should return error, when data source return result error`() =
        runTest(dispatcherProvider.testCoroutineScheduler) {
            val expectedQuery = ""
            val expectedSize = 10
            val expectedOffset = 20

            val expectedError = RootNetworkError.UNEXPECTED_ERROR

            everySuspend {
                mockedDataSource
                    .getProductPreviews(
                        any(), any(), any()
                    )
            } returns Result.Error(expectedError)

            val result = repositoryUnderTest
                .getProductPreviews(
                    query = expectedQuery,
                    size = expectedSize,
                    offset = expectedOffset
                )

            verifySuspend(VerifyMode.exactly(1)) {
                mockedDataSource
                    .getProductPreviews(
                        query = expectedQuery,
                        size = expectedSize,
                        offset = expectedOffset,
                    )
            }

            assertIs<Result.Error<DataError>>(result)
            assertEquals(expectedError, result.error)
        }
}