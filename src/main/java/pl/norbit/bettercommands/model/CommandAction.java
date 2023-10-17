package pl.norbit.bettercommands.model;

import lombok.Data;

import java.util.List;

@Data
public class CommandAction {

    private CommandType type;
    private List<String> action;
}
