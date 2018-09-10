import React, {Component} from "react";
import {Container} from "semantic-ui-react";

class ProgressStep extends Component {
    constructor(props) {
        super(props);
    }
    render() {

        let children = this.props.children;
        let stepNumber = this.props.stepNo;
        return (
          <Container>
              <div>{stepNumber}</div>
              {children}
          </Container>
        );
    }
}

export default ProgressStep;