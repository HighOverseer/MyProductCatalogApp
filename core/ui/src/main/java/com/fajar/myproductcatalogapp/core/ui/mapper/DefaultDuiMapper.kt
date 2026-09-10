package com.fajar.myproductcatalogapp.core.ui.mapper

import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Error
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.core.domain.model.RootError
import com.fajar.myproductcatalogapp.core.domain.model.RootNetworkError
import com.fajar.myproductcatalogapp.core.ui.R
import com.fajar.myproductcatalogapp.core.ui.model.UIText

open class DefaultErrorMapper {
    private val mapNetworkErrorToStringResource = hashMapOf(
        RootNetworkError.UNEXPECTED_ERROR to R.string.telah_terjadi_kesalahan_mohon_coba_lagi,
        RootNetworkError.CONNECTIVITY_UNAVAILABLE to R.string.terdapat_kesalahan_coba_periksa_konektivitas_anda,
        RootNetworkError.NO_CONNECTIVITY_OR_SERVER_UNREACHABLE to R.string.maaf_sepertinya_ada_kesalahan_coba_periksa_koneksi_anda,
        RootNetworkError.REQUEST_TIMEOUT to R.string.terjadi_kesalahan_periska_konektivitas_anda_atau_coba_lagi_nanti,
        RootNetworkError.BAD_REQUEST to R.string.telah_terjadi_kesalahan_mohon_coba_lagi,
        RootNetworkError.FORBIDDEN to R.string.sesi_tidak_valid_mohon_coba_lagi,
        RootNetworkError.UNAUTHORIZED to R.string.sesi_tidak_valid_mohon_coba_lagi,
        RootNetworkError.SERVER_UNAVAILABLE to R.string.maaf_sepertinya_server_sedang_sibuk_coba_lagi_nanti,
        RootNetworkError.INTERNAL_SERVER_ERROR to R.string.telah_terjadi_kesalahan_mohon_coba_lagi_nanti
    )

    private fun getMapper(error: Error): HashMap<out Error, Int>? {
        return when (error) {
            is RootNetworkError -> mapNetworkErrorToStringResource
            else -> null
        }
    }

    open fun <T : RootError> getErrorStringResource(resultError: Result.Error<T>): UIText {
        return getErrorStringResource(resultError.error)
    }

    fun <T : RootError> getErrorStringResource(error: T): UIText {
        if (error is DataError.Unexpected) {
            error.reason?.let { return UIText.Dynamic(it) }
        }

        val defaultStatic = UIText.Static(R.string.telah_terjadi_kesalahan_mohon_coba_lagi)
        val correspondingMap = getMapper(error)
            ?: return defaultStatic

        val stringResource = correspondingMap[error] ?: return defaultStatic
        return UIText.Static(stringResource)
    }
}