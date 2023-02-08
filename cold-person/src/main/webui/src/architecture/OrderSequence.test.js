import React from "react";
import {render, screen} from "@testing-library/react";
import OrderSequence from "./OrderSequence";


describe("the order sequence", () => {
    const orderNumber = 16;
    const orderSequence = [{name: "widget"}];

    test("displays the order number", async () => {
        render(<OrderSequence orderNumber={orderNumber} orderSequence={orderSequence}/>);
        const el = await screen.findByText(/16/);
        expect(el).toBeInTheDocument();
    });

});
