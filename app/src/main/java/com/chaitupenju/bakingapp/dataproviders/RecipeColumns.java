package com.chaitupenju.bakingapp.dataproviders;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public interface RecipeColumns {
    @DataType(INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(TEXT) @NotNull
    String NAME = "name";

    @DataType(TEXT) @NotNull
    String INGREDIENTS = "ingredients";

    @DataType(TEXT) @NotNull
    String STEPS = "steps";

    @DataType(TEXT) @NotNull
    String SERVINGS = "servings";
}
