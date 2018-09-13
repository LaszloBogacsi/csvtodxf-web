import React, {Component} from 'react'
import InputData from "./input-data/InputData";
import Result from "./results/Result";
import {Container, Grid, Step} from "semantic-ui-react";


class CsvToDxf extends Component {
    constructor(props) {
        super(props);
        this.handleConvertResponse = this.handleConvertResponse.bind(this);

        this.state = {
            convertResponse: '',
            isConvertDone: false
        }
    }

    handleConvertResponse(response) {
        this.setState({
            isConvertDone: true,
            convertResponse: response
        })
    }

    render() {
        const convertResponse = this.state.convertResponse;
        let isConvertComplete = this.state.isConvertDone;
        let result;

        if(isConvertComplete) {
            result = <Result convertResponse={convertResponse}/>
        }

        return (
            <Container textAlign='left' style={{width : 900}}>
                <h2>CSV to DXF converter</h2>
                <h4>Convert csv survey data to Cad drawing</h4>
                <Grid>
                    <Grid.Row>
                            <Grid.Column width={8} >
                                <InputData onConvertResponse={this.handleConvertResponse}/>
                            </Grid.Column>
                            <Grid.Column width={8} >
                                {result}
                            </Grid.Column>
                    </Grid.Row>
                </Grid>
            </Container>
        );

    }
}

export default CsvToDxf;