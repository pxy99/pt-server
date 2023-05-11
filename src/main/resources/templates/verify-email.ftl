<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>一页图区操作验证</title>
    <base target="_blank">
    <style rel="stylesheet" type="text/css">
        body {
            font-size: 14px;
            font-family: arial, verdana, sans-serif;
            line-height: 1.666;
            padding: 0;
            margin: 0;
            overflow: auto;
            white-space: normal;
            word-wrap: break-word;
            min-height: 100px
        }

        td, input, button, select, body {
            font-family: Helvetica, 'Microsoft Yahei', verdana
        }

        pre {
            white-space: pre-wrap;
            white-space: -moz-pre-wrap;
            white-space: -o-pre-wrap;
            word-wrap: break-word;
            width: 95%
        }

        th, td {
            font-family: arial, verdana, sans-serif;
            line-height: 1.666
        }

        img {
            border: 0px;
        }

        header, footer, section, aside, article, nav, hgroup, figure, figcaption {
            display: block
        }

        blockquote {
            margin-right: 0px;
        }
    </style>
</head>

<body tabindex="0">
    <div>
        <div style="max-width: 460px;margin: 64px auto;opacity: 0.87;padding: 24px 32px;box-shadow: 0 2px 2px 0 rgba(0, 0, 0, .14), 0 3px 1px -2px rgba(0, 0, 0, .2), 0 1px 5px 0 rgba(0, 0, 0, .12);border-radius: 2px;background: white">
            <h2 style="margin: 0 0 16px 0">一页图区 操作验证</h2>
            <p>您正在请求 <b>注册新用户</b> 的操作验证码, 您的验证码是:</p>
            <h4 style="margin: 0;color: orange">${code}</h4>
            <p>
                请不要向其他人提供此验证码, 这可能使您的账户遭受攻击
                <br>
                这是系统自动发送的邮件，请不要回复此邮件
                <br>
                如果该验证码不是您本人请求的, 请忽略此邮件
            </p>
        </div>
        <div style="clear:both;height:1px;"></div>
    </div>
</body>
</html>