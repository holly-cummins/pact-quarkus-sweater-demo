import React from "react";
import {render, screen} from "@testing-library/react";
import Interaction from "./Interaction";

import userEvent from "@testing-library/user-event"


describe("the interaction", () => {
    const payload = "{fred: mabel}";
    const name = "widget"
    const interaction = {methodName: name, payload};
    let user;

    beforeEach(() => {
        user = userEvent.setup()

    })

    test("displays a name", async () => {
        render(<Interaction interaction={interaction}/>);
        const element = await screen.findByText(name);
        expect(element).toBeInTheDocument();
    });


    it("hides the payload before any hover", async () => {
        render(<Interaction interaction={interaction}/>);
        expect(screen.queryByText(payload)).toBeNull()
    })

    it("hovering on the name brings up a payload", async () => {
        render(<Interaction interaction={interaction}/>);
        await user.hover(screen.getByText(name))
        expect(screen.getByText(payload)).toBeInTheDocument();
    })

    it("leaving the item after hovering hides the payload", async () => {
        const dummyElement = "dummy element"
        render(<div>
            <div>{dummyElement}</div>
            <Interaction interaction={interaction}/></div>);

        await user.hover(screen.getByText(name))
        expect(screen.getByText(payload)).toBeInTheDocument();

        await user.hover(screen.getByText(dummyElement))
        expect(screen.queryByText(payload)).toBeNull()

    })
})

