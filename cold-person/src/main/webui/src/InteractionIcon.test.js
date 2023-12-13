import React from "react"
import {render, screen} from "@testing-library/react"
import InteractionIcon from "./InteractionIcon"


describe("the interaction icon", () => {
    describe("for requests", () => {
        const message = {
            "id": "65799ce810d4eb5b0e55eae1",
            "methodName": "knitSweater",
            "owningComponent": "knitter",
            "payload": {
                "colour": "white",
                "orderNumber": 1
            },
            "correlationId": "1",
            "timestamp": 1702468840285,
            "type": "Request"
        }

        test("displays multiple images", async () => {
            render(<InteractionIcon message={message}/>)
            const images = screen.getAllByRole('img')
            expect(images[0]).toBeInTheDocument()
            expect(images).toHaveLength(2)

        })

        test("displays a colour", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.getAllByRole('img')[0]
            expect(image.src).toContain('white.png')
        })

        test("sets the type", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.getAllByRole('img')[0]
            expect(image.src).toContain('sweater')
        })

        test("constructs an image name", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.getAllByRole('img')[0]
            expect(image).toHaveAttribute('src', 'icons/sweater-white.png')
        })

        test("adds an overlay", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.getAllByRole('img')[1]
            expect(image).toHaveAttribute('src', 'icons/order-overlay.png')
        })
    })

    describe("for failure responses", () => {
        const message = {
            "id": "6579a1b610d4eb5b0e55ec06",
            "methodName": "[response]",
            "owningComponent": "knitter",
            "payload": "Order failed for sweater colour white",
            "correlationId": "1",
            "timestamp": 1702470070466,
            "type": "Response"
        }

        test("does not render an image", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.queryByRole('img')
            expect(image).toBeNull()
        })

        test("renders a placeholder", async () => {
            render(<InteractionIcon message={message}/>)
            const div = screen.getByRole('presentation')
            expect(div).toBeInTheDocument()
        })
    })

    describe("for exception responses", () => {
        const message = {
            "id": "6579a1b610d4eb5b0e55ec06",
            "methodName": "[response]",
            "owningComponent": "knitter",
            "payload": {exception: "bla bla bla"},
            "correlationId": "1",
            "timestamp": 1702470070466,
            "type": "Response"
        }

        test("does not render an image", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.queryByRole('img')
            expect(image).toBeNull()
        })

        test("renders a placeholder", async () => {
            render(<InteractionIcon message={message}/>)
            const div = screen.getByRole('presentation')
            expect(div).toBeInTheDocument()
        })
    })

    describe("for valid responses", () => {
        const message = {
            "id": "6579a43110d4eb5b0e55ec52",
            "methodName": "[response]",
            "owningComponent": "knitter",
            "payload": {
                "colour": "white",
                "orderNumber": 1,
                "style": "LongSleeved"
            },
            "correlationId": "1",
            "timestamp": 1702470705222,
            "type": "Response"
        }

        test("displays an image", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.getByRole('img')
            expect(image).toBeInTheDocument()
        })

        test("displays a colour", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.getByRole('img')
            expect(image.src).toContain('white.png')
        })

        test("sets the type", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.getByRole('img')
            expect(image.src).toContain('sweater')
        })

        test("constructs an image name", async () => {
            render(<InteractionIcon message={message}/>)
            const image = screen.getByRole('img')
            expect(image).toHaveAttribute('src', 'icons/sweater-white.png')
        })
    })


})
