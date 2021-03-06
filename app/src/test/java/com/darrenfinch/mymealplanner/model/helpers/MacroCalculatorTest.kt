package com.darrenfinch.mymealplanner.model.helpers

import com.darrenfinch.mymealplanner.foods.models.domain.Food
import com.darrenfinch.mymealplanner.foods.models.domain.MacroCalculator
import com.darrenfinch.mymealplanner.foods.models.domain.MacroNutrients
import com.darrenfinch.mymealplanner.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.physicalquantities.units.metric.Gram
import com.darrenfinch.mymealplanner.physicalquantities.units.metric.Kilo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MacroCalculatorTest {
    //region Constants -----------------------------------------------------------------------------

    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------

    //endregion Helper Fields ----------------------------------------------------------------------

    //region Set up / Tear down

    @Test
    fun test_updateMacrosForFoodWithNewServingSize() {
        val rice = Food(0, "Rice", PhysicalQuantity(200.0, Gram()), MacroNutrients(170, 37, 4, 2))
        val moreRice = MacroCalculator.getMacrosForFoodWithNewServingSize(rice, PhysicalQuantity(0.5, Gram(
            Kilo()
        )
        ))

        assertEquals(moreRice.macroNutrients.calories.toDouble(), 425.17, 0.1)
    }
    //endregion Set up / Tear down

    //region Tests ---------------------------------------------------------------------------------

    //endregion Tests ------------------------------------------------------------------------------

    //region Helper Classes ------------------------------------------------------------------------

    //endregion Helper Classes ---------------------------------------------------------------------

    //region Helper Methods ------------------------------------------------------------------------

    //endregion Helper Methods ---------------------------------------------------------------------
}