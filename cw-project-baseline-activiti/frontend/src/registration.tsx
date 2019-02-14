import * as React from "react"
import ReactDOM from "react-dom"

import {
    Form, Input, Tooltip, Icon, Cascader, Select, Row, Col, Checkbox, Button, AutoComplete,
} from "antd"
import { FormComponentProps, WrappedFormUtils } from 'antd/lib/form/Form'
import { CheckboxChangeEvent } from "antd/lib/checkbox"

import { FORM_ITEM_LAYOUT, TAIL_FORM_ITEM_LAYOUT } from "./layouts"
import { lm } from "./label_messages_zh"


class UserData {
    username: string = ""
    password: string = ""
    real_name: string = ""
    phone: string = ""
    email: string = ""
    roles: string[] = []
    icon_url: string = ""
}
class RegFormState {
    data: UserData = new UserData()
    confirmDirty: boolean = false
    agreeToTerms: boolean = false
}

class RegForm extends React.Component<FormComponentProps, RegFormState> {

    state = new RegFormState()

    handleSubmit = (e: React.FormEvent<any>) => {
        e.preventDefault()
        const form: WrappedFormUtils = this.props.form

        console.info(form)
    }

    validateToNextPassword = (rule: any, value: any, callback: any) => {
        const form: WrappedFormUtils = this.props.form
        if (value && this.state.confirmDirty) {
            form.validateFields(['confirm'], { force: true })
        }
        callback()
    }

    compareToFirstPassword = (rule: any, value: any, callback: any) => {
        const form: WrappedFormUtils = this.props.form
        if (value && value !== form.getFieldValue('password')) {
            callback(lm.m_inconsistent_passwords)
        } else {
            callback()
        }
    }

    handleConfirmBlur = (e: React.FocusEvent<HTMLInputElement>) => {
        const value = e.target.value
        const s = this.state
        this.setState({
            agreeToTerms: s.agreeToTerms,
            confirmDirty: s.confirmDirty || !!value,
            data: s.data
        })
    }

    agreeToTerm = (e: CheckboxChangeEvent) => {
        const s = this.state
        this.setState({
            agreeToTerms: e.target.checked,
            confirmDirty: s.confirmDirty,
            data: s.data
        })
    }

    render(): JSX.Element {
        const { getFieldDecorator } = this.props.form
        return (<Form onSubmit={this.handleSubmit}>
            <Form.Item {...FORM_ITEM_LAYOUT} label={lm.l_username}>
                {getFieldDecorator("username", {
                    rules: [{ max: 50, required: true, message: lm.m_username_required }]
                })(<Input
                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder={lm.l_username} />)}
            </Form.Item>

            <Form.Item {...FORM_ITEM_LAYOUT} label={lm.l_password}>
                {getFieldDecorator("password", {
                    rules: [{ max: 60, required: true, message: lm.m_password_required },
                    { validator: this.validateToNextPassword }]
                })(<Input
                    prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    type="password"
                    placeholder={lm.l_password} />)}
            </Form.Item>

            <Form.Item {...FORM_ITEM_LAYOUT} label={lm.l_confirm_password}>
                {getFieldDecorator("confirm", {
                    rules: [{ max: 60, required: true, message: lm.m_confirm_password_required },
                    { validator: this.compareToFirstPassword }]
                })(<Input
                    prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    type="password"
                    placeholder={lm.l_password}
                    onBlur={this.handleConfirmBlur} />)}
            </Form.Item>

            <Form.Item {...FORM_ITEM_LAYOUT} label={lm.l_real_name}>
                {getFieldDecorator("real_name", {
                    rules: [{ max: 10, required: true, message: lm.m_real_name_required }]
                })(<Input
                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder={lm.l_real_name} />)}
            </Form.Item>
            <Form.Item  {...FORM_ITEM_LAYOUT} label={lm.l_phone}>
                {getFieldDecorator('phone', {
                    rules: [{ type: 'number', message: lm.m_invalid_phone, },
                    { required: true, message: lm.m_invalid_phone, }],
                })(<Input type="tel"
                    prefix={<Icon type="phone" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder={lm.l_phone} />)}
            </Form.Item>
            <Form.Item  {...FORM_ITEM_LAYOUT} label={lm.l_email}>
                {getFieldDecorator('email', {
                    rules: [{ type: 'email', message: lm.m_invalid_email, },
                    { required: true, message: lm.m_invalid_email, }],
                })(<Input type="email"
                    prefix={<Icon type="email" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder={lm.l_email} />)}
            </Form.Item>
            <Form.Item  {...FORM_ITEM_LAYOUT} label={lm.l_role}>
                {getFieldDecorator('roles', {
                    valuePropName: 'checked',
                })(<Checkbox.Group style={{ width: "100%" }}>
                    <Row>
                        <Col span={8}>
                            <Checkbox value="admin">{lm.l_role_admin}
                                <span>
                                    <Tooltip title={lm.l_role_admin_info}>
                                        <Icon type="question-circle-o" />
                                    </Tooltip>
                                </span>
                            </Checkbox>
                        </Col>
                        <Col span={8}>
                            <Checkbox value="manager">{lm.l_role_manager}
                                <span>
                                    <Tooltip title={lm.l_role_manager_info}>
                                        <Icon type="question-circle-o" />
                                    </Tooltip>
                                </span>
                            </Checkbox>
                        </Col>
                        <Col span={8}>
                            <Checkbox value="operator">{lm.l_role_operator}
                                <span>
                                    <Tooltip title={lm.l_role_operator_info}>
                                        <Icon type="question-circle-o" />
                                    </Tooltip>
                                </span>
                            </Checkbox>
                        </Col>
                    </Row>
                </Checkbox.Group>)}
            </Form.Item>
            <Form.Item {...TAIL_FORM_ITEM_LAYOUT}>
                {getFieldDecorator('agreement', { valuePropName: 'checked', })
                    (<Checkbox onChange={this.agreeToTerm}>{lm.l_confirm_reg} <a href="">{lm.l_user_terms}</a></Checkbox>)}
            </Form.Item>
            <Form.Item {...TAIL_FORM_ITEM_LAYOUT}>
                <Button disabled={!this.state.agreeToTerms} type="primary" htmlType="submit">{lm.l_register}</Button>
            </Form.Item>
        </Form>)
    }
}

const WrappedRegForm = Form.create({ name: "register" })(RegForm)

ReactDOM.render(<WrappedRegForm />,
    document.getElementById("reg_form_div"))