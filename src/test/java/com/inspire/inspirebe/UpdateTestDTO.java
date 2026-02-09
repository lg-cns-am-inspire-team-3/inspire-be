package com.inspire.inspirebe;

import com.inspire.inspirebe.binding.Update;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateTestDTO {
    private Update<String> t1 = Update.absent();
    private Update<String> t2 = Update.absent();
    private Update<String> t3 = Update.absent();
}
