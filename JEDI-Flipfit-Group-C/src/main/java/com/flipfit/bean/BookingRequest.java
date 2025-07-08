package com.flipfit.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

public class BookingRequest {

    @NotEmpty
    private String slotId;

    @JsonProperty
    public String getSlotId() {
        return slotId;
    }

    @JsonProperty
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }
}