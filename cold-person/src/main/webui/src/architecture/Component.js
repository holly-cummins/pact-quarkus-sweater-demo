import styled from "styled-components"

const ComponentName = styled.div`
  font-weight: bold;
  text-align: center;
`

const ComponentDisplay = styled.div`
  background: transparent;
  padding: 1rem 1rem;
  margin: 1rem;
  transition: all .5s ease;
  color: #41403E;
  font-size: 1.5rem;
  letter-spacing: 1px;
  outline: none;
  box-shadow: 2px 8px 4px -6px hsla(0, 0%, 0%, .3);


  border-top-left-radius: 255px 25px;
  border-top-right-radius: 15px 225px;
  border-bottom-right-radius: 225px 15px;
  border-bottom-left-radius: 15px 255px;

  /*
  Above is shorthand for:
  border-top-left-radius: 255px 15px;
  border-top-right-radius: 15px 225px;
  border-bottom-right-radius: 225px 15px;
  border-bottom-left-radius:15px 255px;
  */

  &:hover {
    box-shadow: 20px 38px 34px -26px hsla(0, 0%, 0%, .2);
  }

  border: solid 4px #41403E;


`


const Component = ({component}) => {

    return (

        <ComponentDisplay>
            <ComponentName>{component.name}</ComponentName>
        </ComponentDisplay>
    );
};

export default Component;
