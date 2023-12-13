import styled from "styled-components"


const Holder = styled.div`
  object-fit: contain;
  position: relative;
  top: ${props => props.isResponse ? "3rem" : "0"};
  max-height: 5rem;
  height: 5rem;
  max-width: 5rem;
  display: flex;
  justify-content: center;
  align-items: center;
`

const ContentImage = styled.img`
  object-fit: contain;
  max-height: 5rem;
  max-width: 5rem;
  top: 5rem;
  align-self: flex-start;
`

const UnderlayImage = styled.img`
  object-fit: contain;
  position: absolute;
  max-height: 3.5rem;
  max-width: 3.5rem;
`

const Overlay = styled.img`
  position: relative;
  object-fit: contain;
  max-height: 5rem;
  max-width: 5rem;
`


const types = {"cold-person": "sweater", "knitter": "sweater", "farmer": "wool"}

const InteractionIcon = ({message}) => {
    let img, overlay
    let isResponse = false
    if (message && !message.payload?.exception && typeof message.payload === 'object') {
        const {methodName, owningComponent, payload} = message
        isResponse = methodName === "[response]" // We could also check the 'type' on the message

        if (methodName) {

            const type = types[owningComponent]
            const colour = payload?.colour

            // Render undefined colours, but not undefined types
            if (type) {
                img = (<ContentImage src={`icons/${type}-${colour}.png`}/>)
            }
            if (!isResponse) {
                overlay = <Overlay src={`icons/order-overlay.png`}/>
                img = (<UnderlayImage src={`icons/${type}-${colour}.png`}/>)
            }

        }
    }

    return (<Holder role="presentation" isResponse={isResponse}>{img && img}{overlay && overlay}</Holder>)
}

export default InteractionIcon

