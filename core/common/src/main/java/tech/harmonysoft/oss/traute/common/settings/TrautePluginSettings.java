package tech.harmonysoft.oss.traute.common.settings;

import org.jetbrains.annotations.NotNull;
import tech.harmonysoft.oss.traute.common.instrumentation.InstrumentationType;

import java.util.HashSet;
import java.util.Set;

public class TrautePluginSettings {

    private final Set<String>              notNullAnnotations      = new HashSet<>();
    private final Set<InstrumentationType> instrumentationsToApply = new HashSet<>();

    private final boolean verboseMode;

    public TrautePluginSettings(@NotNull Set<String> notNullAnnotations,
                                @NotNull Set<InstrumentationType> instrumentationsToApply,
                                boolean verboseMode)
    {
        this.notNullAnnotations.addAll(notNullAnnotations);
        this.instrumentationsToApply.addAll(instrumentationsToApply);
        this.verboseMode = verboseMode;
    }

    @NotNull
    public Set<String> getNotNullAnnotations() {
        return notNullAnnotations;
    }

    public boolean isEnabled(@NotNull InstrumentationType type) {
        return instrumentationsToApply.contains(type);
    }

    @NotNull
    public Set<InstrumentationType> getInstrumentationsToApply() {
        return instrumentationsToApply;
    }

    public boolean isVerboseMode() {
        return verboseMode;
    }
}
