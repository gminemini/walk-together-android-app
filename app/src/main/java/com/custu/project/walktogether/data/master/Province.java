package com.custu.project.walktogether.data.master;

/**
 * Created by pannawatnokket on 2/2/2018 AD.
 */

public class Province {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
