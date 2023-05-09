package healthcare.app.fastingtracker.domain.model

import healthcare.app.fastingtracker.CommonParcelable
import healthcare.app.fastingtracker.CommonParcelize

/**
 * @author Jonathan YEUNG
 * @since 2023-04-27
 */
@CommonParcelize
data class FastingResult(
    val endTime: String? = null,
) : CommonParcelable
