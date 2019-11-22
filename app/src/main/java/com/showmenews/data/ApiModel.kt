package com.showmenews.data

import android.app.Person
import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.showmenews.utils.BASE_IMG_URL
import com.showmenews.utils.parseApiError
import kotlinx.android.parcel.Parcelize

enum class ErrorType {
    API,
    HTTP,
    NETWORK,
    INTERNAL,
    UNHANDLED
}

data class ApiResponse(
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("response")
    val response: ResponseWrapper,
    @Expose
    @SerializedName("fault") var fault: Fault?
) {
    val error: Error?
        get() = error?.let { Error(ErrorType.API, hashMapOf("failure" to fault?.errorDescription)) }
}

data class ErrorWrapper(
    @Expose
    @SerializedName("fault") var fault: Fault?
)
data class Fault(
    @Expose
    @SerializedName("faultstring") var errorDescription: String?
)

data class Error(var errorType: ErrorType, var errorHashMap: HashMap<String, String?>) {
    val message: String?
        get() = errorHashMap.values.firstOrNull()

    override fun toString() = with(StringBuilder()) {
        errorHashMap.entries.forEach { (key, value) ->
            append(key)
            append(" : ")
            append("$value")
        }.toString()
    }
}

@Parcelize
data class ResponseWrapper(
    @Expose @SerializedName("docs") var docs: List<Article>? = null
) : Parcelable


@Parcelize
data class Article(
    @Expose
    @SerializedName("_id") var articleId: String?,
    @Expose
    @SerializedName("source") var source: String?,
    @Expose
    @SerializedName("abstract") var abstract: String?,
    @SerializedName("web_url")
    @Expose var webUrl: String?,
    @SerializedName("snippet")
    @Expose var snippet: String?,
    @Expose
    @SerializedName("lead_paragraph") var leadParagraph: String?,
    @SerializedName("multimedia")
    @Expose var multimedia: MutableList<Multimedia>,

    @SerializedName("headline")
    @Expose var headline: Headline?,
    @SerializedName("pub_date")
    @Expose var pubDate: String?,
    @SerializedName("type_of_material")
    @Expose var typeOfMaterial: String?
) : Parcelable {
    val randImgUrl: String?
        get() = multimedia.firstOrNull()?.loadbleUrl
}


@Parcelize
data class Headline(
    @SerializedName("main")
    @Expose var main: String?
) : Parcelable

@Parcelize
data class Multimedia(
    var cropName: String?,
    @SerializedName("url")
    @Expose var url: String?,
    var width: Int?
) : Parcelable {
    val loadbleUrl: String
        get() = "$BASE_IMG_URL$url"
}


data class Legacy(
    @SerializedName("xlarge")
    var xLarge: String?,
    @SerializedName("xlargewidth")
    var xLargeWidth: Int?,
    @SerializedName("xlargeheight")
    var xLargeHeight: Int
)

data class Keyword(
    @SerializedName("major") var major: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("rank") var rank: Int?,
    @SerializedName("value") var value: String?
)

data class Meta(
    @SerializedName("hits")
    var hits: String?,
    @SerializedName("offset")
    var offset: String?,
    @SerializedName("time")
    var time: String?
)

data class Person(
    @SerializedName("firstname")
    var firstName: String? = null,
    @SerializedName("lastname")
    var lastName: String? = null,
    @SerializedName("middlename")
    var middleName: String? = null,
    @SerializedName("organization")
    var organization: String? = null,
    @SerializedName("qualifier")
    var qualifier: String? = null,
    @SerializedName("rank")
    var rank: Int? = 0,
    @SerializedName("role")
    var role: String? = null,
    @SerializedName("title")
    var title: String? = null
)

data class ByLine(
    var organization: String?,
    var original: String?,
    @SerializedName("person") var person: MutableList<Person>? = mutableListOf()
)