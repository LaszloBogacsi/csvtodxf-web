import React, {Component} from 'react'
import InputData from "./input-data/InputData";
import Result from "./results/Result";
import {Container, Grid} from "semantic-ui-react";

class CsvToDxf extends Component {
    constructor(props) {
        super(props);
        this.handleConvertResponse = this.handleConvertResponse.bind(this);

        this.state = {
            convertResponse: ''
        }
    }

    handleConvertResponse(response) {
        this.setState({convertResponse: response})
    }

    render() {
        const convertResponse = this.state.convertResponse;
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
                                <Result convertResponse={convertResponse}/>
                            </Grid.Column>
                    </Grid.Row>
                </Grid>

            </Container>
        );

    }
}

export default CsvToDxf;