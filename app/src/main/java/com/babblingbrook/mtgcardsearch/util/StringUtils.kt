package com.babblingbrook.mtgcardsearch.util

import com.babblingbrook.mtgcardsearch.R
import java.util.regex.Pattern

/**
 * Should probably use an Html parser for anything more complicated than what we are getting
 * from our RSS feed. This is all one source, simple, and consistent, so we use it here.
 */

fun getLink(string: String): String {
    val pattern = Pattern.compile("(?<=href=\")([^\"]*)")
    val matcher = pattern.matcher(string)
    var match: String? = null
    if (matcher.find()) {
        match = matcher.group(0)
    }
    return match ?: ""
}

class StringUtils {

    companion object {
        @JvmStatic
        val replacements =
            mapOf(
                "{T}" to R.drawable.ic_t,
                "{Q}" to R.drawable.ic_q,
                "{E}" to R.drawable.ic_e,
                "{PW}" to R.drawable.ic_pw,
                "{CHAOS" to R.drawable.ic_chaos,
                "{X}" to R.drawable.ic_x,
                "{Y}" to R.drawable.ic_y,
                "{Z}" to R.drawable.ic_z,
                "{0}" to R.drawable.ic_0,
                "{½}" to R.drawable.ic_half,
                "{1}" to R.drawable.ic_1,
                "{2}" to R.drawable.ic_2,
                "{3}" to R.drawable.ic_3,
                "{4}" to R.drawable.ic_4,
                "{5}" to R.drawable.ic_5,
                "{6}" to R.drawable.ic_6,
                "{7}" to R.drawable.ic_7,
                "{8}" to R.drawable.ic_8,
                "{9}" to R.drawable.ic_9,
                "{10}" to R.drawable.ic_10,
                "{11}" to R.drawable.ic_11,
                "{12}" to R.drawable.ic_12,
                "{13}" to R.drawable.ic_13,
                "{14}" to R.drawable.ic_14,
                "{15}" to R.drawable.ic_15,
                "{16}" to R.drawable.ic_16,
                "{17}" to R.drawable.ic_17,
                "{18}" to R.drawable.ic_18,
                "{19}" to R.drawable.ic_19,
                "{20}" to R.drawable.ic_20,
                "{100}" to R.drawable.ic_100,
                "{1000000}" to R.drawable.ic_1000000,
                "{∞}" to R.drawable.ic_infinity,
                "{W/U}" to R.drawable.ic_wu,
                "{W/B}" to R.drawable.ic_wb,
                "{B/R}" to R.drawable.ic_br,
                "{B/G}" to R.drawable.ic_bg,
                "{U/B}" to R.drawable.ic_ub,
                "{U/R}" to R.drawable.ic_ur,
                "{R/G}" to R.drawable.ic_rg,
                "{R/W}" to R.drawable.ic_rw,
                "{G/W}" to R.drawable.ic_gw,
                "{G/U}" to R.drawable.ic_gu,
                "{2/W}" to R.drawable.ic_2w,
                "{2/U}" to R.drawable.ic_2u,
                "{2/B}" to R.drawable.ic_2b,
                "{2/R}" to R.drawable.ic_2r,
                "{2/G}" to R.drawable.ic_2g,
                "{P}" to R.drawable.ic_p,
                "{W/P}" to R.drawable.ic_wp,
                "{U/P}" to R.drawable.ic_up,
                "{B/P}" to R.drawable.ic_bp,
                "{R/P}" to R.drawable.ic_rp,
                "{G/P}" to R.drawable.ic_gp,
                "{HW}" to R.drawable.ic_hw,
                "{HR}" to R.drawable.ic_hr,
                "{W}" to R.drawable.ic_w,
                "{U}" to R.drawable.ic_u,
                "{B}" to R.drawable.ic_b,
                "{R}" to R.drawable.ic_r,
                "{G}" to R.drawable.ic_g,
                "{C}" to R.drawable.ic_c,
                "{S}" to R.drawable.ic_s
            )
    }
}
