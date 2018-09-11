import React, {Component} from 'react'
import InputData from "./input-data/InputData";
import Result from "./results/Result";
import {Container, Grid, Step} from "semantic-ui-react";
import { Router, Route, Switch } from 'react-router'


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

                <Step.Group ordered vertical>
                    <Step completed={false} active={true}>
                        <Step.Content>
                            <Step.Title>Upload</Step.Title>
                            <Step.Description>Upload your csv file</Step.Description>
                        </Step.Content>
                    </Step>
                    <Step completed={false} active={false}>
                        <Step.Content>
                            <Step.Title>Set Options</Step.Title>
                            <Step.Description>Choose drawing options</Step.Description>
                        </Step.Content>
                    </Step>

                    <Step completed={false} active={false}>
                        <Step.Content>
                            <Step.Title>Download</Step.Title>
                            <Step.Description>Download converted drawing</Step.Description>
                        </Step.Content>
                    </Step>

                </Step.Group>

            </Container>
        );

    }
}

export default CsvToDxf;