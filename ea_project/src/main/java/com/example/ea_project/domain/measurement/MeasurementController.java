package com.example.ea_project.domain.measurement;


import com.example.ea_project.domain.user.UserService;
import com.example.ea_project.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Tag(
        name = "Measurements",
        description = "Management of measurements."
)
@RestController
@RequestMapping("measurements")
@Validated
public class MeasurementController {

    private final UserService userService;

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, UserService userService){
        this.measurementService =measurementService;
        this.userService = userService;
    }

    @Operation(
            summary = "Create measurement."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Measurement created."),
            @ApiResponse(responseCode = "409", description = "Wrong user id.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Weight is under zero.", content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping(value = "",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<MeasurementResponse> createMeasurement(@RequestBody @Valid MeasurementRequest measurementRequest){
        Measurement measurement = new Measurement();
        measurementRequest.toMeasurement(measurement, userService);
        Measurement response = measurementService.createMeasurement(measurement);
        return ObjectResponse.of(response, MeasurementResponse::new);
    }

    @Operation(
            summary = "Delete measurement."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Measurement deleted", content = @Content(schema = @Schema(hidden = true))),
    })
    @DeleteMapping(value = "/{id}",produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeasurement(@PathVariable Long id){
        measurementService.deleteMeasurement(id);
    }


}
