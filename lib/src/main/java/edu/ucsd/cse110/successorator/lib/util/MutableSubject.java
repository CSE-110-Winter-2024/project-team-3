package edu.ucsd.cse110.successorator.lib.util;

import androidx.annotation.NonNull;

public interface MutableSubject<T> extends Subject<T> {
    /**
     * Sets the value of the subject and notifies all observers immediately.
     * @param value The new value of the subject.
     */
    void setValue(@NonNull T value);
}