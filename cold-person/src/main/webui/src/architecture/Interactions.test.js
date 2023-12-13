import React from "react"
import {render, screen} from "@testing-library/react"

import Interactions from "./Interactions"
import {sources} from 'eventsourcemock'
import {act} from "react-dom/test-utils"
import {SSEProvider} from "react-hooks-sse"

const emit = (data) => {
    data.forEach(datum => {
        act(() => {
            sources['http://localhost:8088/recorder/interactionstream'].emit(
                'message',
                {data: JSON.stringify(datum)})
        })
    })
}

const payload = (orderNumber) => JSON.stringify({fred: "mabel", orderNumber})


describe("the interaction view", () => {
    const name = "widget"
    const interaction1 = {
        methodName: name + 1,
        owningComponent: "component1",
        type: "request",
        payload: payload(1),
        id: 1,
        correlationId: 1
    }
    const interaction1a = {
        methodName: "other" + name,
        owningComponent: "component2",
        type: "request",
        payload: payload(1),
        id: 11,
        correlationId: 11
    }
    const interaction2 = {
        methodName: name + 2,
        owningComponent: "component3",
        type: "request",
        payload: payload(2),
        id: 12,
        correlationId: 12
    }
    const interaction3 = {
        methodName: name + 3,
        owningComponent: "component4",
        type: "request",
        payload: payload(3),
        id: 13,
        correlationId: 13

    }

    const data = [interaction1, interaction2, interaction1a, interaction3]

    beforeEach(() => {
    })

    test("has some interactions", async () => {
        render(<SSEProvider endpoint="http://localhost:8088/recorder/interactionstream">
            <Interactions/>
        </SSEProvider>)

        emit(data)

        let el = await screen.findByText(/widget1/i)
        expect(el).toBeInTheDocument()

    })

    test("includes all the interaction names", async () => {
        render(<SSEProvider endpoint="http://localhost:8088/recorder/interactionstream">
            <Interactions/>
        </SSEProvider>)
        emit(data)

        let el = await screen.findByText(/widget1/i)
        expect(el).toBeInTheDocument()

        el = await screen.findByText(/widget2/i)
        expect(el).toBeInTheDocument()

        el = await screen.findByText(/widget3/i)
        expect(el).toBeInTheDocument()

        el = await screen.findByText(/otherwidget/i)
        expect(el).toBeInTheDocument()
    })

    test("filters and reverse-sorts by order number", async () => {
        render(<SSEProvider endpoint="http://localhost:8088/recorder/interactionstream">
            <Interactions/>
        </SSEProvider>)
        emit(data)

        // This checks each element is in the page once
        let el1 = await screen.findByText(/widget1/i)
        expect(el1).toBeInTheDocument()

        const el2 = await screen.findByText(/widget2/i)
        expect(el2).toBeInTheDocument()

        const el3 = await screen.findByText(/widget3/i)
        expect(el3).toBeInTheDocument()

        const el1a = await screen.findByText(/otherwidget/i)
        expect(el1a).toBeInTheDocument()


        // The document position return is a bitwise mask

        // Order number 2 should follow order 3
        expect(el3.compareDocumentPosition(el2) & Node.DOCUMENT_POSITION_FOLLOWING).toBeTruthy()

        // Order number 1 should follow order 3
        expect(el3.compareDocumentPosition(el1a) & Node.DOCUMENT_POSITION_FOLLOWING).toBeTruthy()
        expect(el3.compareDocumentPosition(el1) & Node.DOCUMENT_POSITION_FOLLOWING).toBeTruthy()

        // Order number 1 should follow order 2
        expect(el2.compareDocumentPosition(el1a) & Node.DOCUMENT_POSITION_FOLLOWING).toBeTruthy()
        expect(el2.compareDocumentPosition(el1) & Node.DOCUMENT_POSITION_FOLLOWING).toBeTruthy()

    })


})
