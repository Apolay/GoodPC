@GenericGenerator(
        name = "id_generator",
        strategy = "enhanced-sequence",
        parameters = @Parameter(name = "sequence_name", value = "id_generator")
)
package com.dreamteam.goodpc.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;



