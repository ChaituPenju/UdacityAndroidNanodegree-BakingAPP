/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.chaitupenju.bakingapp.dataproviders;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;


@ContentProvider(
        authority = RecipeProvider.AUTHORITY,
        database = RecipeDatabase.class)
public final class RecipeProvider {

    public static final String AUTHORITY = "com.chaitupenju.bakingapp";


    @TableEndpoint(table = RecipeDatabase.RECIPE_BASE)
    public static class RecipeItems {

        @ContentUri(
                path = "recipes",
                type = "vnd.android.cursor.dir/recipes",
                defaultSort = RecipeColumns._ID + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/recipes");
    }
}