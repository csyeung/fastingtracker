package healthcare.app.fastingtracker.domain.model

import healthcare.app.fastingtracker.CommonParcelable
import healthcare.app.fastingtracker.CommonParcelize

/**
 * @author Jonathan YEUNG
 * @since 2023-04-27
 */
@CommonParcelize
data class FastingRecord(
    val startTime: String? = null,
    val endTime: String? = null,
    val elapsedTime: String? = null,
    val remainingTime: String? = null,
) : CommonParcelable
