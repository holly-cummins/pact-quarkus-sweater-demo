import React from "react";
import {render, screen} from "@testing-library/react";
import Component from "./Component";


describe("the interaction", () => {
    const component = {name: "widget"};

    test("displays a name", async () => {
        render(<Component component={component}/>);
        const colour = await screen.findByText(component.name);
        expect(colour).toBeInTheDocument();
    });

});
