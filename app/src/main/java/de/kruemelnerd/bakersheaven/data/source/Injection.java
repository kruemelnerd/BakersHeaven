package de.kruemelnerd.bakersheaven.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {

    public static BakeryRepository provideBakeryRepository(@NonNull Context context){
        checkNotNull(context);

        BakeryRepository repository = BakeryRepository.getInstance(context);
        return repository;
    }
}
